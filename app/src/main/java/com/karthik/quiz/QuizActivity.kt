package com.karthik.quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.google.android.flexbox.FlexboxLayoutManager
import com.karthik.quiz.databinding.ActivityQuizBinding

class QuizActivity : AppCompatActivity(), QuizAdapter.Callback {
    private val viewModel: QuizViewModel by lazy {
        ViewModelProvider(this).get(QuizViewModel::class.java)
    }
    private lateinit var viewBinder: ActivityQuizBinding
    private lateinit var answerAdapter: QuizAdapter
    private lateinit var optionAdapter: QuizAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinder = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(viewBinder.root)
        viewBinder.pauseButton.setOnClickListener {
            viewModel.pauseQuiz()
        }
        viewBinder.resumeButton.setOnClickListener {
            viewModel.resumeQuiz()
        }
        addAdapter()
        addObserver()
        viewModel.nextQuiz()
    }

    override fun onDestroy() {
        removeObserver()
        super.onDestroy()
    }

    private fun addAdapter() {
        answerAdapter = QuizAdapter(this)
        optionAdapter = QuizAdapter(this)
        optionAdapter.callback = this
        viewBinder.answerRecyclerView.layoutManager = FlexboxLayoutManager(this)
        viewBinder.optionRecyclerView.layoutManager = FlexboxLayoutManager(this)
    }

    private fun addObserver() {
        viewModel.timer.observe(this, {
            viewBinder.timerTextView.text = it
        })
        viewModel.answerLiveData.observe(this, {
            answerAdapter.setQuizData(it)
            answerAdapter.notifyDataSetChanged()
        })
        viewModel.optionLiveData.observe(this, {
            optionAdapter.setQuizData(it)
            optionAdapter.notifyDataSetChanged()
        })
    }

    private fun removeObserver() {
        viewModel.timer.removeObservers(this)
    }

    override fun onClick(char: Char) {
        viewModel.optionClick(char)
    }
}