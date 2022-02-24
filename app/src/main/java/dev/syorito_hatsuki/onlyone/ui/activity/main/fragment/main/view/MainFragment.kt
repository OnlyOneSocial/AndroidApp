package dev.syorito_hatsuki.onlyone.ui.activity.main.fragment.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.lifecycleScope
import dev.syorito_hatsuki.onlyone.databinding.FragmentMainBinding
import dev.syorito_hatsuki.onlyone.ui.activity.main.fragment.main.adapter.UserListAdapter
import dev.syorito_hatsuki.onlyone.ui.activity.main.fragment.main.viewmodel.MainViewModel
import kotlinx.coroutines.flow.collect

class MainFragment : Fragment(), LifecycleObserver {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var viewBinding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentMainBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenCreated {
            viewModel.getUsersList().collect {
                viewBinding.recycler.adapter = UserListAdapter(it.users)
            }
        }
    }

    companion object {
        fun newInstance() = MainFragment()
    }

}