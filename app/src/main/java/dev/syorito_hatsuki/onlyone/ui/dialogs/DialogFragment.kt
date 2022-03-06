package dev.syorito_hatsuki.onlyone.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import coil.ImageLoader
import dev.syorito_hatsuki.onlyone.databinding.FragmentDialogsBinding
import dev.syorito_hatsuki.onlyone.ui.users.MainFragmentDirections
import kotlinx.coroutines.flow.collect

class DialogFragment : Fragment() {

    private val viewModel: DialogViewModel by viewModels()
    private var _binding: FragmentDialogsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDialogsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenCreated {
            viewModel.getDialogs().collect {
                val imageLoader = context?.let { it1 ->
                    ImageLoader.Builder(it1)
                        .availableMemoryPercentage(0.25)
                        .crossfade(true)
                        .build()
                }
                imageLoader?.let { it1 ->
                    val adapter = DialogListAdapter(it, it1)
                    binding.recycler.adapter = adapter

                    adapter.onItemClickListener = { position ->
                        val action = DialogFragmentDirections.goToMessages(it[position].sendto)
                        view.findNavController().navigate(action)
                    }
                }
            }
        }
    }
}