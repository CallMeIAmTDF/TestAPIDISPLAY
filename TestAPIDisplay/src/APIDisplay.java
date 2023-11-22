import java.util.ArrayList;
import java.util.List;

public class APIDisplay {
    int personId;
    int parentId;
    ArrayList<Integer> spouseIds;
    String name;
    int groupId;
    String side;
    public APIDisplay(int personId, int parentId, ArrayList<Integer> spouseIds, String name, int groupId, String side) {
        this.personId = personId;
        this.parentId = parentId;
        this.spouseIds = spouseIds;
        this.name = name;
        this.groupId = groupId;
        this.side = side;
    }
    public void display(){
        String strDisplay = "";
        strDisplay += ("ID: " + personId + ", ");
        strDisplay += ("Name: " + name + ", ");
        strDisplay += ("ParentId: " + parentId + ", ");
        strDisplay += ("SpouseIds: [");
        for(int i = 0; i < spouseIds.size() - 1; i++){
            strDisplay += (spouseIds.get(i) + ", ");
        }
        strDisplay += (spouseIds.get(spouseIds.size() - 1) + "], ");
        strDisplay += ("GroupId: " + groupId + ", Side: " + side);
        System.out.println(strDisplay);
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public ArrayList<Integer> getSpouseIds() {
        return spouseIds;
    }

    public void setSpouseIds(ArrayList<Integer> spouseIds) {
        this.spouseIds = spouseIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }
}
