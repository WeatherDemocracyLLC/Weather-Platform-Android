package com.webmobrilweatherapp.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.webmobrilweatherapp.R

class PhotosinstaFragment : Fragment() {
    //    private List<ListModel> listModels;
    //    private RecyclerView recyclephotos;
    //    private SearchViewModel searchViewModel;


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        //        listModels = new ArrayList<>();
//        recyclephotos =root.findViewById(R.id.recyclephotos);
//        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
//        linearLayoutManager1.setOrientation(RecyclerView.VERTICAL);
//        recyclephotos.setLayoutManager(linearLayoutManager1);
//        listModels.clear();
//        listModels.add(new ListModel(R.drawable.recycleview1,R.drawable.rec2,R.drawable.rec3));
//        listModels.add(new ListModel(R.drawable.rec4,R.drawable.rec5,R.drawable.rec6));
//        listModels.add(new ListModel(R.drawable.rec7,R.drawable.rec8,R.drawable.rec9));
//        listModels.add(new ListModel(R.drawable.rec4,R.drawable.rec5,R.drawable.rec6));
//        listModels.add(new ListModel(R.drawable.rec7,R.drawable.rec8,R.drawable.rec9));
//        listModels.add(new ListModel(R.drawable.rec4,R.drawable.rec5,R.drawable.rec6));
//        listModels.add(new ListModel(R.drawable.recycleview1,R.drawable.rec2,R.drawable.rec3));
//        listModels.add(new ListModel(R.drawable.recycleview1,R.drawable.rec2,R.drawable.rec3));
//        listModels.add(new ListModel(R.drawable.rec7,R.drawable.rec8,R.drawable.rec9));
//        if (listModels.size() > 0) {
//            recyclephotos.setAdapter(new myadapter(getContext(), listModels));
//        }
        return inflater.inflate(R.layout.fragment_photosinsta, container, false)
    }
}