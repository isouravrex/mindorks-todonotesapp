package com.mindorks.todonotesapp.onboarding

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.mindorks.todonotesapp.R


class OnBoardingTwoFragment : Fragment() {

    lateinit var textViewNext : TextView
    lateinit var textViewBack : TextView
    lateinit var onOptionClick : OnOptionClick

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onOptionClick = context as OnOptionClick
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_on_boarding_two_activity, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
    }

    private fun bindViews(view: View) {
        textViewNext = view.findViewById(R.id.textViewNext)
        textViewBack = view.findViewById(R.id.textViewBack)
        clickListeners()
    }

    private fun clickListeners() {
        textViewBack.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                onOptionClick.onOptionBack()

            }

        })
        textViewNext.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                onOptionClick.onOptionNext()

            }

        })
    }
    interface OnOptionClick{
        fun onOptionBack()
        fun onOptionNext()
    }


}
