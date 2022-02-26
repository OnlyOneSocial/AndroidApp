package dev.syorito_hatsuki.onlyone.ui.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import dev.syorito_hatsuki.onlyone.R
import dev.syorito_hatsuki.onlyone.databinding.FragmentHomeBinding
import dev.syorito_hatsuki.onlyone.ui.MainViewModel
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
                val adapter = UserListAdapter(it.users)

                binding.recycler.adapter = adapter

                adapter.onItemClickListener = { position ->
                    val action = MainFragmentDirections.goToUserPage(it.users[position].id)
                    view.findNavController().navigate(action)
                }
            }
        }
    }


}