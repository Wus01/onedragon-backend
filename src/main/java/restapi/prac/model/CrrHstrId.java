package restapi.prac.model;

import java.io.Serializable;
import java.util.Objects;

public class CrrHstrId implements Serializable {

    private Integer userId;
    private Integer storeId;

    public CrrHstrId() {}

    public CrrHstrId(Integer userId, Integer storeId) {
        this.userId = userId;
        this.storeId = storeId;
    }
    
    /*다른 객체지만 동일한 키값일때 동일객체로 인식할수 있도록 override함*/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CrrHstrId)) return false;
        CrrHstrId that = (CrrHstrId) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(storeId, that.storeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, storeId);
    }
}
