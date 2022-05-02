package co.develhope.chooseyourownbeer.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.develhope.chooseyourownbeer.BeerAction
import co.develhope.chooseyourownbeer.BeerAdapter
import co.develhope.chooseyourownbeer.Beers
import co.develhope.chooseyourownbeer.Beers.getBeerFromId
import co.develhope.chooseyourownbeer.R
import co.develhope.chooseyourownbeer.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val beerList = Beers.getBeers()

        binding.beerList.apply {
            adapter = BeerAdapter(beerList) { action -> OnAdapterClick(action) }
            layoutManager= LinearLayoutManager(context)
        }

    }
        private fun OnAdapterClick(action:BeerAction){
            when (action) {
    //          BeerAction.OnStarClick
              is BeerAction.OnGoToDetailPageClick -> {
                    val idBeer= action.beer
                  val bundle= bundleOf("BEER_ID" to idBeer)
                    findNavController().navigate(R.id.action_navigation_home_to_beerDetail, bundle)
                }
            }
        }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}