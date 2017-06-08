package org.max.model;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;


@MappedSuperclass
public abstract class BaseUuidEntity {

	@Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id", length = 32)
    private String id;

    @Column(name = "delete_flag", length = 1)
    private String deletedFlag = String.valueOf(Boolean.FALSE);

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

	public String getDeletedFlag() {
		return deletedFlag;
	}

	public void setDeletedFlag(String deletedFlag) {
		this.deletedFlag = deletedFlag;
	}
    
}
