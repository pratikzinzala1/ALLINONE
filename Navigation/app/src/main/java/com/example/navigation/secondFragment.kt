package com.example.navigation

import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.navigation.databinding.FragmentSecondBinding
import com.example.navigation.firstfragment.FirstViewModel


class secondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding
    private lateinit var name: String
    private val firstViewModel:FirstViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        name = arguments?.getString("name").toString()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding!!.firstviewmodel = firstViewModel
        binding!!.lifecycleOwner = viewLifecycleOwner

        binding!!.btn1.setOnClickListener {
            firstViewModel.change()
        }


        val animation = TransitionInflater.from(requireContext()).inflateTransition(
            android.R.transition.move
        )
        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}