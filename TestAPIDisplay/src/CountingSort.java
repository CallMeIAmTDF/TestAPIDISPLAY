import java.util.ArrayList;

public class CountingSort {
    public Person[] countSort(ArrayList<Person> persons, int size){

        Person[] out = new Person[size + 1];

        //tìm max
        int max = persons.get(0).getParentId();
        for(int i = 1; i < size; i++){
            if(persons.get(i).getParentId() > max){
                max = persons.get(i).getParentId();
            }
        }


        int[] count = new int[max + 1];
        for(int i = 0; i < max; i++){
            count[i] = 0;
        }

        //
        for (int i = 0; i < size; i++) {
            count[persons.get(i).getParentId()] += 1;
        }

        for (int i = 1; i <= max; i++) {
            count[i] += count[i - 1];
        }

        for(int i = size - 1; i >= 0; i--){
            out[count[persons.get(i).getParentId()] - 1] = persons.get(i);
            count[persons.get(i).getParentId()] = count[persons.get(i).getParentId()] - 1;
        }
        return out;
    }
}
