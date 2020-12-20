package com.hok.forgod.pojo.interfaces

import com.hok.forgod.pojo.model.Reciter
import com.hok.forgod.pojo.model.Sura
import com.hok.forgod.pojo.model.Traklist

interface ListLisenar {
   fun paslisttrack(List:List<Traklist>,list2: List<Sura>,lsitReaders:List<Reciter>)
}