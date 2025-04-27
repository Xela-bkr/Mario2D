package com.example.mario2d.menu;

import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.mario2d.R;

/**
 * Fragment général pour afficher le niveau que je joueur doit sélectionner
 * Chaque élément est dimentionné en fonction de la taille de l'écran
 * Certains objets sont aussi grands en largeur qu'en longueyr : sol, blocs, etc...
 * D'autres nécessitent qu'on préserve une harmonie entre hauteur et largeur. C'est pour cela qu'il faut calculer leur coefficient d'agrandissement et de réduction : mario et luigi par exemple.
 *
 * Abstract Fragment possède un fichie xml qui défini un patron unique pour chaque élément.
 * En fonction des niveaux, les ressources et les couleurs sont changées.
 */
public class AbstractFragment extends Fragment {

    /**
     * Coefficients
     */
    public float MAIN_CHAR_AGR_COEFF = 1.86f;
    public float MAIN_CHAR_RED_COEFF = 0.536f;
    public float PIPE_AGR_COEFF = 1.076f;
    /**
     * Le personnage principal affiché à l'écran est une simple ImageView.
     */
    public ImageView mainChar;

    /**
     * Attributs représentant l'ensemble des mesures de chaque objet : largeur et hauteur
     */
    public DisplayMetrics dm;
    public int displayWidth, displayHeight, CHARACTER_WIDTH, CHARACTER_HEIGHT, FLOOR_WIDTH, FLOOR_HEIGHT, FLOOR_RATE;
    public int CASTLE_WIDTH, CASTLE_HEIGHT, BLOC_WIDTH, BLOC_HEIGHT, PIPE_WIDTH, PIPE_HEIGHT;
    public int floorResource, castleResource, charResource, pairBlocResource, impairBlocResource,
            pipeResource, backgroundColor;

    /**
     * Mettre un constructeur basique su style public Constructor(args){...} fonctionne mais il arrive fréquemment qu'android tue et recréée
     * des instances de Fragment par lui-même. Lorsque cela se produit, Android appelle un constructeur vide Constructor(). Sachant que le rendu de notre
     * fragment dépend de ses arguments, il faut implémenter le constructeur de sorte à ce que les données soient sauvegardées.
     * newInstance permet de passer des arguments proprements dans le constructeur et de les sauvegarder .
     *
     * @param floorResource
     * @param castleResource
     * @param charResource
     * @param pairBlocResource
     * @param impairBlocResource
     * @param pipeResource
     * @param backgroundColor
     * @return
     */
    public static AbstractFragment newInstance(int floorResource, int castleResource, int charResource,
                                               int pairBlocResource, int impairBlocResource, int pipeResource, int backgroundColor){
        AbstractFragment abstractFragment = new AbstractFragment();
        Bundle args = new Bundle();

        args.putInt("floor", floorResource);
        args.putInt("castle", castleResource);
        args.putInt("character", charResource);
        args.putInt("pairBloc", pairBlocResource);
        args.putInt("impairBloc", impairBlocResource);
        args.putInt("pipe", pipeResource);
        args.putInt("bgCol", backgroundColor);

        abstractFragment.setArguments(args);
        return abstractFragment;
    }

    /**
     * onCreate instancie l'ensemble des dimensions des objets.
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null){
            floorResource = getArguments().getInt("floor");
            castleResource = getArguments().getInt("castle");
            charResource = getArguments().getInt("character");
            pairBlocResource = getArguments().getInt("pairBloc");
            impairBlocResource = getArguments().getInt("impairBloc");
            pipeResource = getArguments().getInt("pipe");
            backgroundColor = getArguments().getInt("bgCol");
        }
        dm = requireContext().getResources().getDisplayMetrics();
        displayWidth = dm.widthPixels;
        displayHeight = dm.heightPixels;

        CHARACTER_WIDTH = (int)(0.035*displayWidth);
        CHARACTER_HEIGHT = (int)(MAIN_CHAR_AGR_COEFF*CHARACTER_WIDTH);

        FLOOR_WIDTH = (int)(0.06*displayWidth);
        FLOOR_HEIGHT = FLOOR_WIDTH;
        FLOOR_RATE = (int)displayWidth/FLOOR_WIDTH + 1;

        CASTLE_WIDTH = (int)(0.25*displayWidth);
        CASTLE_HEIGHT = CASTLE_WIDTH;

        BLOC_WIDTH = (int)(0.04*displayWidth);
        BLOC_HEIGHT = BLOC_WIDTH;

        System.out.printf("Bloc width : %d \n", BLOC_WIDTH);

        PIPE_WIDTH = (int)(0.06*displayWidth);
        PIPE_HEIGHT = (int)(PIPE_AGR_COEFF*PIPE_WIDTH);
    }

    /**
     * onCreateView est la fonction qui génère la View qui est affichée à l'écran
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.abstract_fragment_layout, container, false);

        setFloorLayout(view, FLOOR_RATE, FLOOR_WIDTH, FLOOR_HEIGHT, floorResource);
        setCastleImageView(view, CASTLE_WIDTH, CASTLE_HEIGHT, castleResource);
        setChar(view, CHARACTER_WIDTH,  CHARACTER_HEIGHT, charResource);
        setBlocLayout(view, BLOC_WIDTH,  BLOC_HEIGHT, pairBlocResource, impairBlocResource);
        setPipe(view, PIPE_WIDTH,  PIPE_HEIGHT, pipeResource);
        setMainLayoutBackgroundColor(view, backgroundColor);

        return view;
    }

    /**
     * Dessine le sol dans le View
     * @param view
     * @param n
     * @param width
     * @param height
     * @param resource
     */
    public void setFloorLayout(View view,int n, int width, int height, int resource){
        LinearLayout layout = view.findViewById(R.id.floorlayout_fragment);
        for(int i = 0; i<n; i++){
            ImageView floor = new ImageView(getContext());
            floor.setImageResource(resource);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
            floor.setLayoutParams(params);
            layout.addView(floor);
        }
    }

    /**
     * Dessine le château dans le View
     * @param view
     * @param width
     * @param height
     * @param resource
     */
    public void setCastleImageView(View view, int width, int height, int resource){
        ImageView castle = view.findViewById(R.id.castle_fragment);
        castle.setImageResource(resource);
        LinearLayout.LayoutParams castleParams = new LinearLayout.LayoutParams(
                width, height
        );
        castleParams.setMargins(0, (int)(displayHeight-CASTLE_HEIGHT-FLOOR_HEIGHT), 0, 0);
        castle.setLayoutParams(castleParams);
    }

    /**
     * Dessine le personnage principal dans la View
     * @param view
     * @param width
     * @param height
     * @param resource
     */
    public void setChar(View view, int width, int height, int resource){
        mainChar = view.findViewById(R.id.player_fragment);
        this.charResource = resource;
        mainChar.setImageResource(resource);
        LinearLayout.LayoutParams charParams = new LinearLayout.LayoutParams(width, height);
        mainChar.setLayoutParams(charParams);
    }

    /**
     * Dessine les blocs dans la View
     * @param view
     * @param width
     * @param height
     * @param pairResource
     * @param impairResource
     */
    public void setBlocLayout(View view, int width, int height, int pairResource, int impairResource){
        LinearLayout layout = view.findViewById(R.id.boxLayout_fragment);
        for(int i = 0; i<3; i++){
            ImageView bloc = new ImageView(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
            bloc.setLayoutParams(params);
            switch(i%2){
                case 0 :
                    bloc.setImageResource(pairResource);
                    break;
                case 1 :
                    bloc.setImageResource(impairResource);
                    break;
            }
            layout.addView(bloc);
        }
    }

    /**
     * Dessine le tuyau vert dans la View
     * @param view
     * @param width
     * @param height
     * @param resource
     */
    public void setPipe(View view, int width, int height, int resource){
        ImageView pipe = view.findViewById(R.id.pipe_fragment);
        LinearLayout.LayoutParams pipeParams = new LinearLayout.LayoutParams(width, height);
        pipe.setLayoutParams(pipeParams);
    }

    /**
     * Défini un fond d'écran
     * @param view
     * @param color
     */
    public void setMainLayoutBackgroundColor(View view, int color){
        int col = ContextCompat.getColor(getContext(), color);
        ConstraintLayout layout = view.findViewById(R.id.abstract_fragment_parentlayout);
        layout.setBackgroundColor(col);
    }
}
