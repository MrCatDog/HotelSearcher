package com.example.hotelsearcher

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hotelsearcher.databinding.TryAgainFragmentBinding

class TryAgainFragment(private val listener: View.OnClickListener) : Fragment() {

    private var _binding: TryAgainFragmentBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TryAgainFragmentBinding.inflate(inflater)
        binding.tryAgainBtn.setOnClickListener {
            listener.onClick(it)
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}