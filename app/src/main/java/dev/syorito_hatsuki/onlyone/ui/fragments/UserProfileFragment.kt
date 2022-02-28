package dev.syorito_hatsuki.onlyone.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import coil.load
import coil.transform.CircleCropTransformation
import dev.syorito_hatsuki.onlyone.R
import dev.syorito_hatsuki.onlyone.databinding.FragmentBlankBinding
import dev.syorito_hatsuki.onlyone.ui.MainActivity
import kotlinx.coroutines.flow.collect

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "UserID"

/**
 * A simple [Fragment] subclass.
 * Use the [BlankFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BlankFragment : Fragment(), LifecycleObserver {
    // TODO: Rename and change types of parameters
    private var param1: Int? = null
    private val viewModel: UserPage by viewModels()
    private var _binding: FragmentBlankBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        val slideshowViewModel =
            ViewModelProvider(this).get(UserPage::class.java)

        _binding = FragmentBlankBinding.inflate(inflater, container, false)

        val textView: TextView = binding.Username
        slideshowViewModel.text.observe(viewLifecycleOwner) {
            param1?.let {
                textView.text = it.toString()
            }
        }

        return binding.root
    }



    @SuppressLint("SetTextI18n", "ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenCreated {
            param1?.let { it ->
                viewModel.getUserInfo(it).collect {
                    binding.UserImage.load("https://cdn.only-one.su/public/clients/${it.user.id}/${it.user.avatar}") {
                        error(R.drawable.no_avatar)
                        transformations(CircleCropTransformation())
                    }

                    (activity as MainActivity).setTitle(it.user.username)

                    when(val num = System.currentTimeMillis()/1000 -it.user.online){
                        in 0..60*3 -> binding.Online.text = "Онлайн"
                        in 60*3..60*60 -> binding.Online.text = "${num/60} минут назад"
                        in 60*60..60*60*24 -> binding.Online.text = "${num/60/60} час назад"
                        in 60*60*24..60*60*24*31 -> binding.Online.text = "${num/60/60/24} день назад"
                        else -> {
                            binding.Online.text =  DateUtils.formatDateTime(context, it.user.online*1000, DateUtils.FORMAT_SHOW_DATE );
                        }
                    }

                    binding.Username.text = it.user.username
                    binding.Bio.text = it.user.bio
                }
            }
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BlankFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: Int) =
            BlankFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                }
            }
    }
}