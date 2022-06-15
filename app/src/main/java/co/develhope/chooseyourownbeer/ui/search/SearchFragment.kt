package co.develhope.chooseyourownbeer.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import co.develhope.chooseyourownbeer.*
import co.develhope.chooseyourownbeer.databinding.FragmentSearchBinding
import co.develhope.chooseyourownbeer.ui.model.BeerUi
import co.develhope.chooseyourownbeer.ui.BeerAction
import co.develhope.chooseyourownbeer.ui.BeerAdapter
import co.develhope.chooseyourownbeer.ui.detail.BeerDetailActivity

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var listFiltered: List<BeerUi>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextChange(query: String?): Boolean {
                if (query != null) {
                    listFiltered = Beers.getFilteredBeer(query)
                    val count = listFiltered.size
                    printCount(count)
                    showListFiltered()
                }
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    listFiltered = Beers.getFilteredBeer(query)
                    val count = listFiltered.size
                    printCount(count)
                    showListFiltered()
                }
                return true
            }
        })
    }

    private fun printCount(count: Int) {
        if (count != 0) {
            binding.textResult.text = getString(R.string.search_result_text, count.toString())
        } else {
            binding.textResult.text = getString(R.string.search_result_nothing_text)
        }
    }

    private fun showListFiltered() {
        binding.beerListSearch.apply {
            val listFiltered = Beers.getFilteredBeer(binding.searchView.query.toString())
            val sortedList = listFiltered.sortedWith(compareBy<BeerUi> { it.favourite }.reversed().thenBy { it.id })
            adapter = BeerAdapter(sortedList) { action -> onAdapterClick(action) }
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun onAdapterClick(action: BeerAction) {
        when (action) {
            is BeerAction.OnStarClick -> {
                Beers.switchFavorite(action.beerUi)
                showListFiltered()
            }
            is BeerAction.OnGoToDetailPageClick -> {
                val idBeer = action.beerUi.id
                val intent = Intent(context, BeerDetailActivity::class.java)
                intent.putExtra("BEER_ID", idBeer)
                startActivity(intent)
            }
            else -> {
                return
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}