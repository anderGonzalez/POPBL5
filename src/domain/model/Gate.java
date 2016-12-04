package domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Gate {

	@Id
	@GeneratedValue
	private Integer id;
	private Integer number;

	// TODO tagak jarri
	@OneToOne
	Node positionNode;

	public Node getPositionNode() {
		return positionNode;
	}

	public void setPositionNode(Node positionNode) {
		this.positionNode = positionNode;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

}
