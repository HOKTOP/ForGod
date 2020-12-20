package com.hok.forgod.ui.Fragment.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.barteksc.pdfviewer.PDFView
import com.hok.forgod.R

class DashboardFragment : Fragment() {
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
    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
                ViewModelProvider(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        var pdfViewa = root.findViewById<PDFView>(R.id.pdfView)
        pdfViewa.fromAsset("00003.pdf")
            .swipeHorizontal(true)
            .load()
        return root
    }
}