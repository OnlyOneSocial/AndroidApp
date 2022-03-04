package dev.syorito_hatsuki.onlyone.ui.dialogs.messages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dev.syorito_hatsuki.onlyone.databinding.FragmentDialogsBinding
import kotlinx.coroutines.flow.collect

private const val ARG_PARAM1 = "UserID"

class MessageFragment : Fragment() {

    private val viewModel: DialogViewModel by viewModels()
    private var _binding: FragmentDialogsBinding? = null
    private var param1: Int? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
        }
    }


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
            var userid = 0
            param1?.let { it -> userid = it }
            viewModel.getMessages(userid).collect {
                //binding.recycler.layoutManager = GridLayoutManager(requireContext(), 3)


                    val adapter = MessageListAdapter(it)
                    binding.recycler.adapter = adapter

                    adapter.onItemClickListener = { position ->
                        //val action = DialogFragmentDirections.goToUserPage(it.users[position].id)
                        //view.findNavController().navigate(action)
                    }

            }
        }
    }
}