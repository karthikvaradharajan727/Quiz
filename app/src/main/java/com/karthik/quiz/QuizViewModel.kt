package com.karthik.quiz

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.FieldPosition

class QuizViewModel : ViewModel() {
    private val quizRepository = QuizRepository()
    private val quizData = quizRepository.getQuizData()
    var currentQuestionData: QuizRepository.QuizData? = null
    val answerLiveData = MutableLiveData<ArrayList<QuizCharacter>>()
    val optionLiveData = MutableLiveData<ArrayList<QuizCharacter>>()
    private var answerData: ArrayList<QuizCharacter>? = null
    private var optionData: ArrayList<QuizCharacter>? = null
    private var questionIndex = -1
    fun nextQuiz() {
        questionIndex++
        pauseQuiz()
        remainingTime = 600000
        setCurrentQuestionData(questionIndex)
        resumeQuiz()
    }

    private fun setCurrentQuestionData(position: Int) {
        currentQuestionData = quizData[position]
        answerData = ArrayList<QuizCharacter>().apply {
            currentQuestionData?.word?.let {
                for (char in it) {
                    add(QuizCharacter(char.toString(), QuizCharacter.Status.EMPTY))
                }
            }
        }
        optionData = ArrayList<QuizCharacter>().apply {
            currentQuestionData?.options?.let {
                for (char in it) {
                    add(QuizCharacter(char.toString(), QuizCharacter.Status.ENABLE))
                }
            }
        }
        answerLiveData.value = answerData
        optionLiveData.value = optionData
    }

    fun optionClick(char: Char) {
        for (data in answerData ?: ArrayList()) {
            if (data.text[0] == char) {
                data.status = QuizCharacter.Status.ENABLE
            }
        }
        for (data in optionData ?: ArrayList()) {
            if (data.text[0] == char) {
                data.status = QuizCharacter.Status.DISABLE
            }
        }
        answerLiveData.value = answerData
        optionLiveData.value = optionData
    }


    data class QuizCharacter(val text: String, var status: Status) {
        enum class Status {
            DISABLE, ENABLE, EMPTY
        }
    }

    /*
    * Timer logic starts
    * */
    val timer = MutableLiveData<String>()
    var countDownTimer: CountDownTimer? = null
    var remainingTime: Long = 600000

    fun pauseQuiz() {
        countDownTimer?.cancel()
    }

    fun resumeQuiz() {
        countDownTimer = CountDown(remainingTime, 1000, {
            remainingTime = it
            timer.value = setTime((it / 1000).toInt())
        }, {
            timer.value = setTime((0).toInt())
            remainingTime = 0
        }).start()
    }

    private fun setTime(seconds: Int): String {
        val minute: Int = seconds / 60
        val secondsRemaining: Int = seconds % 60
        return "${if (minute > 0) "${minute}m " else ""}${secondsRemaining}second${if (secondsRemaining == 1) "" else "s"}"
    }

    class CountDown(
        millisInFuture: Long,
        countDownInterval: Long,
        val ticker: (Long) -> Unit,
        val finish: () -> Unit
    ) : CountDownTimer(millisInFuture, countDownInterval) {
        override fun onTick(millisUntilFinished: Long) {
            ticker(millisUntilFinished)
        }

        override fun onFinish() {
            finish()
        }
    }
    /*
    * Timer logic ends
    * */
}