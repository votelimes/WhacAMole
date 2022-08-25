package com.votelimes.whacamole.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.google.android.material.button.MaterialButton;
import com.votelimes.whacamole.R;
import com.votelimes.whacamole.databinding.FragmentScoreBinding;
import com.votelimes.whacamole.viewmodel.ScoreViewModel;

public class ScoreFragment extends Fragment {

    private ScoreViewModel viewModel;
    private FragmentScoreBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel =
                new ViewModelProvider(this).get(ScoreViewModel.class);

        binding = FragmentScoreBinding.inflate(inflater, container, false);

        try {
            viewModel.setScore(getArguments().getInt("score"));
        }
        catch (NullPointerException e){
            e.printStackTrace();
            viewModel.setScore(-1);
        }

        binding.setVm(viewModel);
        binding.executePendingBindings();

        viewModel.updateHighscore(getContext());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MaterialButton playAgainButton = view.findViewById(R.id.play_again);
        playAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStartGame();
            }
        });

        MaterialButton backHomeButton = view.findViewById(R.id.home);
        backHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackHome();
            }
        });

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Score");

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        else {
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void onStartGame(){
        NavController nc = Navigation.findNavController(getView());
        NavDirections direction = ScoreFragmentDirections.actionScoreFragmentToGameFragment();
        nc.navigate(direction);
    }

    public void onBackHome(){
        NavController nc = Navigation.findNavController(getView());
        NavDirections direction = ScoreFragmentDirections.actionScoreFragmentToHomeFragment();
        nc.navigate(direction);
    }
}