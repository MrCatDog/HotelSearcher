package com.example.hotelsearcher.shared.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hotelsearcher.additional_info_activity.HotelFragment
import com.example.hotelsearcher.databinding.TryAgainFragmentBinding
import com.example.hotelsearcher.main.FullHotelInfo
import com.example.hotelsearcher.shared.Constants

const val ERROR_MSG_TAG = "error"

class TryAgainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = TryAgainFragmentBinding.inflate(inflater)

        binding.errorText.text = requireArguments().getString(ERROR_MSG_TAG)

        binding.tryAgainBtn.setOnClickListener {
            (requireActivity() as View.OnClickListener).onClick(it) //todo такое преобразование вообще нормально?
        }
        return binding.root
    }

    companion object TryAgainFragmentFactory {
        fun newInstance(error: String): TryAgainFragment {
            val myFragment = TryAgainFragment()
            val args = Bundle()

            args.putString(ERROR_MSG_TAG, error)

            return myFragment.also { it.arguments = args }
        }
    }
}