package com.webmobrilweatherapp.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.webmobrilweatherapp.R

class PostsinstaFragment : Fragment() {
//        private List<InstaPostData> listModelsdata;
//        private RecyclerView recyclepostinsta;
//        private SearchViewModel searchViewModel;


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        //        listModelsdata = new ArrayList<>();
//        recyclepostinsta =root.findViewById(R.id.recyclepostinsta);
//        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
//        linearLayoutManager1.setOrientation(RecyclerView.VERTICAL);
//        recyclepostinsta.setLayoutManager(linearLayoutManager1);
//        listModelsdata.clear();
//        listModelsdata.add(new InstaPostData());
//        listModelsdata.add(new InstaPostData());
//        listModelsdata.add(new InstaPostData());
//        listModelsdata.add(new InstaPostData());
//        listModelsdata.add(new InstaPostData());
//        listModelsdata.add(new InstaPostData());
//        listModelsdata.add(new InstaPostData());
//        if (listModelsdata.size() > 0) {
//            listModelsdata.add(new InstaPostData());
//            recyclepostinsta.setAdapter(new InstaPostAdapter(getContext(), listModelsdata));
//        }
        return inflater.inflate(R.layout.fragment_postsinsta, container, false)
    }
}