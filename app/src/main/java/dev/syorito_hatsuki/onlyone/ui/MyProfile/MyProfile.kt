package dev.syorito_hatsuki.onlyone.ui.MyProfile

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import dev.syorito_hatsuki.onlyone.databinding.ActivityMainBinding
import dev.syorito_hatsuki.onlyone.databinding.FragmentDashboardBinding
import dev.syorito_hatsuki.onlyone.databinding.FragmentHomeBinding
import dev.syorito_hatsuki.onlyone.databinding.FragmentMainBinding
import kotlinx.coroutines.flow.collect

class MyProfile : Fragment(),LifecycleObserver  {

    private val viewModel: MainViewModel by viewModels()
    //private lateinit var viewBinding: FragmentHomeBinding
    private var _binding: FragmentDashboardBinding? = null

    private val binding get() = _binding!!

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val slideshowViewModel =
            ViewModelProvider(this).get(SlideshowViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)


        val textView: TextView = binding.textDashboard
        slideshowViewModel.text.observe(viewLifecycleOwner) {
            textView.text = "Привет из OnlyOne"
        }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}