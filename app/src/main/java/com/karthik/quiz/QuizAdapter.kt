package com.karthik.quiz

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.karthik.quiz.databinding.ItemCharBinding

class QuizAdapter(
    context: Context,
    private val data: ArrayList<QuizViewModel.QuizCharacter> = ArrayList<QuizViewModel.QuizCharacter>()
) :
    RecyclerView.Adapter<QuizAdapter.CustomViewHolder>() {
    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    var callback: Callback? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CustomViewHolder(ItemCharBinding.inflate(layoutInflater, parent, false))

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.onBind(data[position])
    }

    override fun getItemCount() = data.size

    fun setQuizData(quizData: ArrayList<QuizViewModel.QuizCharacter>) {
        data.clear()
        data.addAll(quizData)
    }

    inner class CustomViewHolder(private val viewBinder: ItemCharBinding) :
        RecyclerView.ViewHolder(viewBinder.root), View.OnClickListener {
        var char: Char? = null

        init {
            viewBinder.characterTextView.setOnClickListener(this)
        }

        fun onBind(data: QuizViewModel.QuizCharacter) {
            char = data.text[0]
            when (data.status) {
                QuizViewModel.QuizCharacter.Status.DISABLE -> {
                    viewBinder.characterTextView.isEnabled = false
                    viewBinder.characterTextView.text = data.text
                }
                QuizViewModel.QuizCharacter.Status.ENABLE -> {
                    viewBinder.characterTextView.isEnabled = true
                    viewBinder.characterTextView.text = data.text
                }
                QuizViewModel.QuizCharacter.Status.EMPTY -> {
                    viewBinder.characterTextView.text = ""
                }
            }
        }

        override fun onClick(v: View?) {
            char?.let { callback?.onClick(it) }
        }
    }

    interface Callback {
        fun onClick(char: Char)
    }
}