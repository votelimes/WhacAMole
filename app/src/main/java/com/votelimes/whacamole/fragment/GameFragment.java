package com.votelimes.whacamole.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.votelimes.whacamole.R;
import com.votelimes.whacamole.databinding.FragmentGameBinding;
import com.votelimes.whacamole.interfaces.Scorable;
import com.votelimes.whacamole.viewmodel.GameViewModel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class GameFragment extends Fragment {

    private GameViewModel viewModel;
    private FragmentGameBinding binding;
    private Timer finishGameTimer;
    private List<HoleAndMole> holesAndMoles;
    private ExecutorService showMoleExecutor;
    private Future moleExecutorThreadFuture;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        showMoleExecutor = Executors.newSingleThreadExecutor();
        viewModel =
                new ViewModelProvider(this).get(GameViewModel.class);

        holesAndMoles = new ArrayList<>(viewModel.ACTIVE_HOLES_COUNT);

        binding = FragmentGameBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.setVm(viewModel);
        binding.executePendingBindings();

        finishGameTimer = new Timer();
        finishGameTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if((viewModel.getTime() - 1) == -1){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            finishGame();
                        }
                    });
                }
                viewModel.setTime(viewModel.getTime() - 1);
            }
        }, 1000, 1000);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        holesAndMoles = selectActiveMoles(view, viewModel);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        else {
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        moleExecutorThreadFuture = showMoleExecutor.submit(new Runnable() {
            @Override
            public void run() {
                int moleIndex = -1;
                int newMoleIndex = -2;
                try {
                    while(true){
                        while(viewModel.shouldShowNext()){
                            newMoleIndex = viewModel.getNextShownMoleIndex();
                            if(moleIndex != newMoleIndex){
                                moleIndex = newMoleIndex;
                                holesAndMoles.get(moleIndex).showMole();
                            }
                            Thread.sleep(250);
                        }
                        Thread.sleep(200);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        finishGameTimer.cancel();
        showMoleExecutor.shutdown();
        moleExecutorThreadFuture.cancel(true);
        holesAndMoles.forEach(item -> {
            try{
                item.hideMoleTimer.cancel();
            } catch (NullPointerException ignored){}
        });
        super.onDestroyView();
    }

    public void finishGame(){
        finishGameTimer.cancel();
        NavController nc = Navigation.findNavController(getView());
        NavDirections direction = GameFragmentDirections.actionGameFragmentToScoreFragment();
        direction.getArguments().putInt("score", viewModel.getScore());
        nc.navigate(direction);
    }


    public List<HoleAndMole> selectActiveMoles(@NonNull View molesRootView, @NonNull Scorable counter){
        List<HoleAndMole> result = new LinkedList<>();
        viewModel
                .getActiveMoles()
                .forEach(item -> {
                    ImageView hole;
                    ImageView mole;

                    if(item == 1){
                        hole = (ImageView) molesRootView.findViewById(R.id.hole1);
                        mole = (ImageView) molesRootView.findViewById(R.id.mole1);

                        result.add(new HoleAndMole(getContext(), hole, mole, counter));
                    }
                    else if(item == 2){
                        hole = (ImageView) molesRootView.findViewById(R.id.hole2);
                        mole = (ImageView) molesRootView.findViewById(R.id.mole2);

                        result.add(new HoleAndMole(getContext(), hole, mole, counter));
                    }
                    else if(item == 3){
                        hole = (ImageView) molesRootView.findViewById(R.id.hole3);
                        mole = (ImageView) molesRootView.findViewById(R.id.mole3);

                        result.add(new HoleAndMole(getContext(), hole, mole, counter));
                    }
                    else if(item == 4){
                        hole = (ImageView) molesRootView.findViewById(R.id.hole4);
                        mole = (ImageView) molesRootView.findViewById(R.id.mole4);

                        result.add(new HoleAndMole(getContext(), hole, mole, counter));
                    }
                    else if(item == 5){
                        hole = (ImageView) molesRootView.findViewById(R.id.hole5);
                        mole = (ImageView) molesRootView.findViewById(R.id.mole5);

                        result.add(new HoleAndMole(getContext(), hole, mole, counter));
                    }
                    else if(item == 6){
                        hole = (ImageView) molesRootView.findViewById(R.id.hole6);
                        mole = (ImageView) molesRootView.findViewById(R.id.mole6);

                        result.add(new HoleAndMole(getContext(), hole, mole, counter));
                    }
                    else if(item == 7){
                        hole = (ImageView) molesRootView.findViewById(R.id.hole7);
                        mole = (ImageView) molesRootView.findViewById(R.id.mole7);

                        result.add(new HoleAndMole(getContext(), hole, mole, counter));
                    }
                    else if(item == 8){
                        hole = (ImageView) molesRootView.findViewById(R.id.hole8);
                        mole = (ImageView) molesRootView.findViewById(R.id.mole8);

                        result.add(new HoleAndMole(getContext(), hole, mole, counter));
                    }
                    else if(item == 9){
                        hole = (ImageView) molesRootView.findViewById(R.id.hole9);
                        mole = (ImageView) molesRootView.findViewById(R.id.mole9);

                        result.add(new HoleAndMole(getContext(), hole, mole, counter));
                    }
                    else{
                        new MaterialAlertDialogBuilder(getActivity())
                                .setTitle("An error acquired")
                                .setMessage("A critical error has occurred. Contact support. Error code 2.")
                                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        getActivity().finish();
                                    }
                                })
                                .show();
                        throw new IllegalStateException("Unable to find correct mole and hole number");
                    }

        });
        return result;
    }

    private class HoleAndMole {
        private final int MOLE_SHOW_TIME = 500;
        private ImageView hole;
        private ImageView mole;
        private final Scorable counter;
        private Timer hideMoleTimer;
        private Animation moleOnShow;
        private Animation moleAfterHit;
        private boolean blocked;

        private View.OnClickListener moleOnClickListener;

        public HoleAndMole(Context context, @NonNull ImageView hole, @NonNull ImageView mole, @NonNull Scorable counter){
            this.hole = hole;
            this.mole = mole;
            this.counter = counter;

            moleOnShow = AnimationUtils.loadAnimation(context, R.anim.mole_on_show);
            moleOnShow.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    mole.setVisibility(View.VISIBLE);
                    hideMoleTimer = new Timer();
                    hideMoleTimer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if(isMoleVisible()){
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if((moleOnShow != null && !(moleOnShow.hasStarted() && !moleOnShow.hasEnded()))) {
                                            hideMole();
                                        }
                                    }
                                });
                            }
                        }
                    }, MOLE_SHOW_TIME);
                }

                @Override
                public void onAnimationEnd(Animation animation) {

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            moleAfterHit = AnimationUtils.loadAnimation(context, R.anim.mole_after_hit);
            moleAfterHit.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    mole.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationEnd(Animation animation) {

                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    hole.setVisibility(View.VISIBLE);
                }
            });

            moleOnClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!blocked) {
                        if (hideMoleTimer != null) {
                            hideMoleTimer.cancel();
                        }
                        blocked = true;
                        counter.addScorePoint();
                        mole.startAnimation(moleAfterHit);
                    }

                }
            };
            this.mole.setOnClickListener(moleOnClickListener);
        }

        synchronized public void hideMole(){
            this.mole.setVisibility(View.GONE);
            counter.removePlayer();
            blocked = false;
        }

        synchronized public void showMole(){
            if(!isMoleVisible()){
                blocked = false;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mole.startAnimation(moleOnShow);
                    }
                });
            }
        }

        synchronized public boolean isMoleVisible(){
            return mole.getVisibility() == View.VISIBLE;
        }
    }
}