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
import com.amian.donezo.DonezoActivity
import com.amian.donezo.R
import com.amian.donezo.database.entities.Todo
import com.amian.donezo.databinding.EmptyTodoListBinding
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
		(requireActivity() as DonezoActivity).lockDrawer(locked = false)
		return binding.root
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setHasOptionsMenu(true)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		(requireActivity() as AppCompatActivity).supportActionBar?.let {
			it.show()
			it.title = getString(R.string.todos)
		}

		binding.recyclerview.adapter = TodoListAdapter()
		binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())

		viewModel.listItemsLiveData.observe(viewLifecycleOwner) { list ->
			Timber.d("The length of the list to display is: ${list?.size}")
			(binding.recyclerview.adapter as TodoListAdapter).submitList(list)
		}
	}

	override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
		super.onCreateOptionsMenu(menu, inflater)
		menu.clear()
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
		ListAdapter<ListItem, RecyclerView.ViewHolder>(object : DiffUtil.ItemCallback<ListItem>() {

			override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem) =

				when (oldItem) {
					is ListItem.TodoListItem -> oldItem.todo.id == (newItem as? ListItem.TodoListItem)?.todo?.id
					is ListItem.EmptyListItem -> newItem is ListItem.EmptyListItem
				}


			override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem) =
				oldItem == newItem

		}) {

		override fun getItemViewType(position: Int) = getItem(position).viewType

		override fun onCreateViewHolder(
			parent: ViewGroup,
			viewType: Int
		): RecyclerView.ViewHolder =
			when (ListItemType.values()[viewType]) {
				ListItemType.TODO -> TodoViewHolder(parent)
				ListItemType.EMPTY -> EmptyViewHolder(parent)
			}

		override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
			getItem(position).let { item ->
				when (item) {
					is ListItem.TodoListItem -> (holder as TodoViewHolder).bind(item)
					is ListItem.EmptyListItem -> (holder as EmptyViewHolder)
				}
			}

		}

		override fun getItemCount(): Int = currentList.size

		private inner class TodoViewHolder(private val binding: ListItemTodoBinding) :
			RecyclerView.ViewHolder(binding.root) {
			constructor(parent: ViewGroup) : this(
				ListItemTodoBinding.inflate(layoutInflater, parent, false)
			)

			fun bind(todoListItem: ListItem.TodoListItem) {
				todoListItem.todo.let {
					binding.todo = it
					when (it.done) {
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
					binding.checkbox.setOnClickListener { checkbox ->
						when ((checkbox as CheckBox).isChecked) {
							true -> viewModel.markTodoDone(
								email = it.email,
								todoId = it.id,
								done = true
							)
							false -> viewModel.markTodoDone(
								email = it.email,
								todoId = it.id,
								done = false
							)
						}
					}
				}
			}
		}

		private inner class EmptyViewHolder(private val binding: EmptyTodoListBinding) :
			RecyclerView.ViewHolder(binding.root) {
			constructor(parent: ViewGroup) : this(
				EmptyTodoListBinding.inflate(layoutInflater, parent, false)
			)
		}
	}

	enum class ListItemType {
		EMPTY,
		TODO
	}

	sealed class ListItem(val viewType: Int) {

		class EmptyListItem : ListItem(ListItemType.EMPTY.ordinal)

		class TodoListItem(val todo: Todo) : ListItem(ListItemType.TODO.ordinal)

	}


}