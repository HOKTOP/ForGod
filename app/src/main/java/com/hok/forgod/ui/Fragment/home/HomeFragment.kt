package com.hok.forgod.ui.Fragment.home

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isEmpty
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ethanhua.skeleton.Skeleton
import com.hok.forgod.R
import com.hok.forgod.pojo.Adaptors.OnCarItemClickListner2
import com.hok.forgod.pojo.Adaptors.adaptorReaders
import com.hok.forgod.pojo.interfaces.ListLisenar
import com.hok.forgod.pojo.model.Reciter
import com.hok.forgod.pojo.model.Sura
import com.hok.forgod.pojo.model.Traklist
import com.hok.forgod.ui.Settingapp
import kotlinx.android.synthetic.main.actionbar.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.io.File

class HomeFragment : Fragment(), OnCarItemClickListner2, SearchView.OnQueryTextListener {
    /*


 ____   ____         _____     ____    ____             _____        ______    ____      ____
|    | |    |   ____|\    \   |    |  |    |        ___|\    \   ___|\     \  |    |    |    |
|    | |    |  /     /\    \  |    |  |    |       |    |\    \ |     \     \ |    |    |    |
|    |_|    | /     /  \    \ |    | /    //       |    | |    ||     ,_____/||    |    |    |
|    .-.    ||     |    |    ||    |/ _ _//        |    | |    ||     \--'\_|/|    |    |    |
|    | |    ||     |    |    ||    |\    \'        |    | |    ||     /___/|  |    |    |    |
|    | |    ||\     \  /    /||    | \    \        |    | |    ||     \____|\ |\    \  /    /|
|____| |____|| \_____\/____/ ||____|  \____\       |____|/____/||____ '     /|| \ ___\/___ / |
|    | |    | \ |    ||    | /|    |   |    |      |    /    | ||    /_____/ | \ |   ||   | /
|____| |____|  \|____||____|/ |____|   |____|      |____|____|/ |____|     | /  \|___||___|/
  \(     )/       \(    )/      \(       )/          \(    )/     \( |_____|/     \(    )/
   '     '         '    '        '       '            '    '       '    )/         '    '
                                                                        '


    *|
     */
    private lateinit  var adaptORReader: adaptorReaders
    private var sourlsist: List<Sura> = listOf()
    var position: Int? =null
    private lateinit var homeViewModel: HomeViewModel
    private var lsitReaders:List<Reciter> = listOf()
        lateinit var passlist:ListLisenar
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
            SearchinRecyler?.setOnQueryTextListener(this)


        return root
    }

    ///o View Is Created
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.getReaderonlien()
        homeViewModel.getSoraoffonlien()
        homeViewModel.ReadDBRciters.observe(viewLifecycleOwner, Observer {
            lsitReaders = it
            if (ReaderRcy.isEmpty()){
                setuprecylerReaders()
            }
        })
        homeViewModel.TrakLIVE.observe(viewLifecycleOwner, Observer {
            Passdatatoactivte(it)
        })
        homeViewModel.Surafilterrecycler.observe(viewLifecycleOwner, Observer {
            sourlsist = it
        })
        passlist = activity as ListLisenar
        if (ReaderRcy.isEnabled){
            ReaderRcy.setHasFixedSize(true)
        }
    }

    private fun Passdatatoactivte(it: List<Traklist>?) {
        passlist.paslisttrack(it!!,sourlsist,lsitReaders)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ResourceType")
    private fun setuprecylerReaders() {
        ReaderRcy.layoutManager = LinearLayoutManager(context)
        adaptORReader = adaptorReaders(this)
        adaptORReader.setdata(lsitReaders)
        var SkeletonScreen = Skeleton.bind(ReaderRcy)
            .adapter(adaptORReader)
            .load(R.layout.layout_default_item_skeleton)
            .show();
        ReaderRcy.postDelayed(kotlinx.coroutines.Runnable {
            SkeletonScreen.hide()
        }, 300)
        var settingapp = Settingapp()
        if(settingapp.themestting(null,requireContext())){
            ReaderRcy.setIndexBarColor(R.color.colorPrimaryDark)
            ReaderRcy.setIndexBarStrokeVisibility(true)
            ReaderRcy.setIndexBarTextColor(R.color.colorAccent)
            ReaderRcy.setIndexBarStrokeColor("#ffa449")
        }else{
            ReaderRcy.setIndexBarColor(R.color.white)
            ReaderRcy.setIndexBarStrokeVisibility(true)
            ReaderRcy.setIndexBarTextColor(R.color.colorAccent)
            ReaderRcy.setIndexBarStrokeColor("#ffa449")
        }
        activity?.title_activity?.text = getText(R.string.alqruan)
    }

    override fun onItemClick(view: View, item: List<Reciter>, position: Int) {
        homeViewModel.Traklist(position,homeViewModel.filterrecycler(position),item)
        val dir = File(context?.filesDir, "/Downloaded/${item.get(position).id}")
        dir.mkdirs()
        this.position = position
        var file = context?.getSharedPreferences("filedownload",MODE_PRIVATE)?.edit()
        file?.putString("File",item.get(position).id.toString())
        file?.commit()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        adaptORReader.filter.filter(query)
        return false

    }

    override fun onQueryTextChange(newText: String?): Boolean {
        adaptORReader.filter.filter(newText)
        return false

    }
}