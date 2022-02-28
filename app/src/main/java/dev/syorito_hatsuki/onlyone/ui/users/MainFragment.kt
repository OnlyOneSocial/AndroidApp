package dev.syorito_hatsuki.onlyone.ui.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import coil.ImageLoader
import dev.syorito_hatsuki.onlyone.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.collect

class MainFragment : Fragment()  {

    private val viewModel: MainViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenCreated {
            viewModel.getUsersList().collect {
                binding.recycler.layoutManager = GridLayoutManager(requireContext(), 3)
                val imageLoader = context?.let { it1 ->
                    ImageLoader.Builder(it1)
                        .availableMemoryPercentage(0.25)
                        .crossfade(true)
                        .build()
                }
                 imageLoader?.let { it1 ->
                     val adapter =  UserListAdapter(it.users, it1)
                     binding.recycler.adapter = adapter

                     adapter.onItemClickListener = { position ->
                       val action = MainFragmentDirections.goToUserPage(it.users[position].id)
                       view.findNavController().navigate(action)
                     }
                }
            }
        }
    }


}