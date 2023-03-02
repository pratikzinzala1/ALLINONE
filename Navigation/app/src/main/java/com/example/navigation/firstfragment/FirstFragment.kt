package com.example.navigation.firstfragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.example.navigation.R
import com.example.navigation.databinding.FragmentFirstBinding


class firstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FirstViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("TEST", "fragment oncreat")


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        Log.d("TEST", "fragment oncreatview")

        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btn1.setOnClickListener {
            viewModel.change()
        }
        viewModel.textdata.observe(viewLifecycleOwner) {
            binding.tv1.text = it
        }
        binding.btnnextscreen.setOnClickListener {
            val extras = FragmentNavigatorExtras(binding.productImage to "image_big")
            findNavController().navigate(
                R.id.action_firstFragment_to_secondFragment,
                null,
                null,
                extras
            )
        }


    }


}