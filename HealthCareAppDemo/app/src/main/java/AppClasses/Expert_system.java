package AppClasses;

import android.widget.ArrayAdapter;

/**
 * Created by Mustafa_X on 3/30/2016.
 */
public class Expert_system {
// private String[] Features = {"F1","F2","F3","F4","F5","F6","F7","F8"};// read from it's file

   // public ArrayList<String> Features = new ArrayList<>() ;// read from it's file
   // public ArrayList<String> clases = new ArrayList<>();

    public  String[] Features;// Name of diseases
    public String[] Clases ;



    public int[][] Train_data;/* = {{1,1,1, 0,0,0,1,0},
                                 {0,1,0,1,0,1,1,0},
                                 {0,0,0,1,1,1,1,1},
                                 {1,0,1,0,1,0,1,1}};*/
    private String detected_issue ;

    public int[] Test_data  ;    // from input of patient

    public String[] Input_features ;//={"F1","F3","F5"};

    public double[][] Result  ;

    public Expert_system() {

     /*   Clases.add("A");
        Clases.add("B");
        Clases.add("C");
        Clases.add("D");
        */

    }

    public String getDetected_issue() {
        return detected_issue;


    }

    public void setDetected_issue(String detected_issue) {
        this.detected_issue = detected_issue;
    }
/*
    public ArrayList<String> getFeatures() {
        return Features;
    }

    public void setFeatures(ArrayList<String> features) {
        Features = features;
    }

    */

    public String[] getFeatures() {
        return Features;
    }

    public void setFeatures(String[] features) {
        Features = features;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////

    // Methods

    // calculate ecludian distance
    //  hwa ana msh 3arf aktbha 3la el lab bs hshr7ha kwis
    public void KNN_Algorithm_calculate_Eculidian_distance()
    {

        Justify_Testdata_from_input_features();

        for (int i = 0; i < 4; i++)
        {
            double count=0;
            for (int j = 0; j < 8; j++)
            {
                double temp =Math.pow((Train_data[i][j] - Test_data[j]), 2);
                if(temp == 0){
                    count++;
                }
                Result[i][0] += temp;

            }
            Result[i][0] = Math.sqrt(Result[i][0]);
            Result[i][1] = (count/Features.length)*100;
        }
    }

    // get min  distance that is the nearest class (most match class with features)
    public boolean get_index_of_min_distance_else_error()
    {
        Double min = Double.MAX_VALUE;
        int index =0;
        for(int i=0 ; i<Result.length ; i++)
        {
            if(Result[i][0]<min){
                min = Result[i][0];
                index = i ;
            }
            else if(Result[i][0] == min)
            {
                return  false;
            }
        }

        if(Result[index][1]<50) {
            return  false ;
        }
        else {
            setDetected_issue(Clases[index] + " with " + Result[index][1] + " % ");
        }
        return  true;

    }

    public void Justify_Testdata_from_input_features() {
        int count =0;
        Test_data = new int[Features.length];
        for(int i=0 ; i< Input_features.length ; i++)
        {
            for(int j=0 ; j<Features.length ; j++)
            {
                if(Input_features[i]==Features[j])
                {
                    Test_data[j] = 1;
                    break;
                }
            }
        }
    }

    // set Input data
    public void fill_input_features(ArrayAdapter<String> adapter){
        Input_features = new String[adapter.getCount()];
        for(int i=0 ; i< adapter.getCount() ; i++){
           Input_features[i] = adapter.getItem(i);
        }
    }

}


