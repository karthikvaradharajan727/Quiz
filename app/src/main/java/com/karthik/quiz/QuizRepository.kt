package com.karthik.quiz

import org.json.JSONArray

class QuizRepository {
    fun getQuizData() = ArrayList<QuizData>().apply {
        for (i in 0 until QUIZ_JSON.length()) {
            val quiz = QUIZ_JSON.getJSONObject(i)
            val array = ArrayList(quiz.getString("name").toCharArray().toList())
            add(QuizData(quiz.getString("imgUrl"), array, ArrayList(CharRange('A', 'Z').toList())))
        }
    }

    data class QuizData(
        val image: String,
        val word: ArrayList<Char>,
        val options: ArrayList<Char>,
        var score: Int? = null
    )

    companion object {
        val QUIZ_JSON =
            JSONArray("[{\"imgUrl\":\"http://www.dsource.in/sites/default/files/resource/logo-design/logos/logos-representing-india/images/01.jpeg\",\"name\":\"AADHAAR\"},{\"imgUrl\":\"https://static.digit.in/default/thumb_101067_default_td_480x480.jpeg\",\"name\":\"PHONEPE\"},{\"imgUrl\":\"https://cdn.iconscout.com/icon/free/png-256/bhim-3-69845.png\",\"name\":\"BHIM\"},{\"imgUrl\":\"https://media.glassdoor.com/sqll/300494/flipkart-com-squarelogo-1433217726546.png\",\"name\":\"FLIPKART\"},{\"imgUrl\":\"http://logok.org/wp-content/uploads/2014/05/Walmart-Logo-880x645.png\",\"name\":\"WALMART\"},{\"imgUrl\":\"http://www.thestylesymphony.com/wp-content/uploads/2015/05/Myntra-logo.png\",\"name\":\"MYNTRA\"}]")
    }
}