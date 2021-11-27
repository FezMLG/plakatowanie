package com.fezmlg;

import java.io.*;
import java.util.*;

class Main implements Runnable{
    public static void main(String[] args){
        new Thread(new Main()).start();
    }

    int numberOfBuildings, minHeight, numberOfPosters;
    int MAX = 1000000000;

    long sum(ArrayList<Integer> m, int start){
        long sum = 0;
        for(int i = start; i < m.size(); i++){
            sum += m.get(i);
        }
        return sum;
    }

    public void run(){
        try{

            /*
             reading data from user input
             */
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String line = br.readLine();
            StringTokenizer st = new StringTokenizer(line);
            numberOfBuildings = Integer.parseInt(st.nextToken());
            ArrayList<Integer> heightOfBuilding = new ArrayList<>();

            for (int i = 0; i < numberOfBuildings; i++){
                line = br.readLine();
                st = new StringTokenizer(line);

                /*
                 taking first number from line and doing nothing with it
                 because we don't need width of building
                 */
                st.nextToken();

                /*
                 taking second number from line and adding it to height array
                 */
                int w = Integer.parseInt(st.nextToken());
                heightOfBuilding.add(w);
            }
            minHeight = Collections.min(heightOfBuilding);

            /*
              loop for posters, ends loop when there is no more buildings left
              */
            do {
                System.out.println();
                for (int i = 0; i < heightOfBuilding.size(); i++) {
                    int currentHeight = heightOfBuilding.get(i);
                    int nextHeight = 0;
                    boolean nextExist = true;

                    /*
                      Printing buildings
                     */
                    if(currentHeight != 0){
                        System.out.printf("%5s", heightOfBuilding.get(i));
                    } else {
                        System.out.printf("%5s", "");
                    }

                    if(currentHeight == 0) continue;

                    /*
                      Getting height of next building
                      and checking if there is another building
                      */
                    try {
                        if(heightOfBuilding.get(i + 1) > 0){
                            nextHeight = heightOfBuilding.get(i + 1);
                        }
                    } catch ( IndexOutOfBoundsException ignored){ nextExist = false; }

                    /*
                      Removing building height that has a piece of poster
                      */
                    heightOfBuilding.set(i, currentHeight - minHeight);
                    if(heightOfBuilding.get(i) < 0){
                        heightOfBuilding.set(i, 0);
                    }

                    /*
                     when building ends
                     */
                    if (nextHeight - minHeight < 0) {
                        minHeight = MAX;

                        /*
                          if next value of heightOfBuilding does not exist or the rest of array are only zeros
                          find a new one from beginning of array
                          */
                        if(!nextExist || (sum(heightOfBuilding, i+1)) <= 0){
                            for(int a : heightOfBuilding){
                                /*
                                  if we are going out of block (0) break because we found it
                                 */
                                if(a == 0 && minHeight != MAX) break;
                                if(a < minHeight && a > 0) {
                                    minHeight = a;
                                }
                            }
                        } else {
                            /*
                              if next value in heightOfBuilding arraylist does exist, look for new minHeight for block
                              from next index where previous block ends
                              */
                            int start = i + 1;
                            for (int x = start; x < heightOfBuilding.size(); x++) {
                                int temp = heightOfBuilding.get(x);
                                if (temp <= 0 && minHeight != MAX) {
                                    break;
                                }
                                if (temp <= 0){
                                    continue;
                                }
                                if (temp < minHeight) {
                                    minHeight = temp;
                                }
                            }
                        }
                        numberOfPosters += 1;
                    }
                }
            }while(sum(heightOfBuilding, 0) != 0);

            System.out.println("\n Number of posters: " + numberOfPosters);

        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
