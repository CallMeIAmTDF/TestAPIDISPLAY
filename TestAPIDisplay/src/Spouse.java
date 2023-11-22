public class Spouse {
    Integer spouseId;
    Integer husbandId;
    Integer wifeId;

    public Spouse(int spouseId, int husbandId, int wifeId) {
        this.spouseId = spouseId;
        this.husbandId = husbandId;
        this.wifeId = wifeId;
    }

    public int getSpouseId() {
        return spouseId;
    }

    public void setSpouseId(int spouseId) {
        this.spouseId = spouseId;
    }

    public int getHusbandId() {
        return husbandId;
    }

    public void setHusbandId(int husbandId) {
        this.husbandId = husbandId;
    }

    public int getWifeId() {
        return wifeId;
    }

    public void setWifeId(int wifeId) {
        this.wifeId = wifeId;
    }
}
