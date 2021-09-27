package com.amian.donezo.views

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.amian.donezo.ApplicationNavigationDirections
import com.amian.donezo.R
import com.amian.donezo.database.entities.Todo
import com.amian.donezo.databinding.FragmentHomeBinding
import com.amian.donezo.databinding.ListItemTodoBinding
import com.amian.donezo.repositories.UserRepository
import com.amian.donezo.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    val binding
        get() = _binding!!

    @Inject
    lateinit var userRepository: UserRepository

    private val todoFragment by lazy {
        AddTodoFragment()
    }

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.authenticated.observe(viewLifecycleOwner) {
            if (!it) findNavController().navigate(ApplicationNavigationDirections.actionUnauthenticated())
        }

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerview.adapter = TodoListAdapter()
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())

        viewModel.todosLiveData.observe(viewLifecycleOwner) { list ->
            Timber.d("The length of the todo list is: ${list.size}")
            if (list.isEmpty()) {
                binding.recyclerview.visibility = View.GONE
                binding.emptyList.root.visibility = View.VISIBLE
            } else {
                binding.recyclerview.visibility = View.VISIBLE
                binding.emptyList.root.visibility = View.GONE
                (binding.recyclerview.adapter as TodoListAdapter).submitList(list)
            }

        }

        binding.logoutButton.setOnClickListener {
            lifecycleScope.launch { userRepository.clearUser() }
        }

        with(requireActivity() as AppCompatActivity) {
            setSupportActionBar(binding.toolbar)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.home_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add -> {
                todoFragment.show(requireActivity().supportFragmentManager, AddTodoFragment.TAG)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private inner class TodoListAdapter :
        ListAdapter<Todo, TodoListAdapter.TodoViewHolder>(object : DiffUtil.ItemCallback<Todo>() {

            override fun areItemsTheSame(oldItem: Todo, newItem: Todo) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Todo, newItem: Todo) =
                oldItem == newItem

        }) {

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): TodoListAdapter.TodoViewHolder = TodoViewHolder(parent)

        override fun onBindViewHolder(holder: TodoListAdapter.TodoViewHolder, position: Int) {
            holder.bind(getItem(position))
        }

        override fun getItemCount(): Int = currentList.size

        private inner class TodoViewHolder(private val binding: ListItemTodoBinding) :
            RecyclerView.ViewHolder(binding.root) {
            constructor(parent: ViewGroup) : this(
                ListItemTodoBinding.inflate(layoutInflater, parent, false)
            )

            fun bind(todo: Todo) {
                binding.text.text = todo.todo
                binding.checkbox.setOnClickListener { checkbox ->
                    when ((checkbox as CheckBox).isChecked) {
                        true -> {
                            binding.text.apply {
                                paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                            }
                        }
                        false -> {
                            binding.text.apply {
                                paintFlags = paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                            }
                        }
                    }
                }
            }
        }
    }
}