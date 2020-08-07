package onlineshop.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sizes")
public class BoardSize extends BaseEntity {

    private String size;

    public BoardSize() {
    }

    @Column(name = "size", nullable = false, unique = true)
    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }


}
