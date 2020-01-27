package com.sygneto.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

/**
 * A task_mgt.
 */
@Entity
@Table(name = "task_mgt")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TaskMgt extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long taskId;
	
    @Column(name = "user_name")
    private String userName;
    
    @Column(name = "title")
    private String title;
    
    @Column(name = "notes")
    private String notes;
    
    @Column(name = "start_date")
    private Instant startDate; 

    @Column(name = "end_date")
    private Instant endDate; 
    
    @Column(name = "priority")
    private String priority;

    @Column(name = "is_completed")
    private boolean isCompleted;

    @Column(name = "is_starred")
    private boolean isStarred;
    
    @Column(name = "status")
    private String status;
    
    @OneToMany(targetEntity = ActivityMgt.class, cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	 @JoinColumn(name="task_id" , referencedColumnName = "task_id")
	  private List<ActivityMgt> activityMgt;

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Instant getStartDate() {
		return startDate;
	}

	public void setStartDate(Instant startDate) {
		this.startDate = startDate;
	}

	public Instant getEndDate() {
		return endDate;
	}

	public void setEndDate(Instant endDate) {
		this.endDate = endDate;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public boolean isCompleted() {
		return isCompleted;
	}

	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	public boolean isStarred() {
		return isStarred;
	}

	public void setStarred(boolean isStarred) {
		this.isStarred = isStarred;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	

	public List<ActivityMgt> getActivityMgt() {
		return activityMgt;
	}

	public void setActivityMgt(List<ActivityMgt> activityMgt) {
		this.activityMgt = activityMgt;
	}

	@Override
	public String toString() {
		return "TaskMgt [taskId=" + taskId + ", userName=" + userName + ", title=" + title + ", notes=" + notes
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", priority=" + priority + ", isCompleted="
				+ isCompleted + ", isStarred=" + isStarred + ", status=" + status + "]";
	}
    
}
