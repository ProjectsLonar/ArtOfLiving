package com.lonar.artofliving.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = "lt_aol_banners")
@JsonInclude(Include.NON_NULL)
public class LtAolBanners extends BaseClass{

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "banner_id")
	private Long bannerId;
	
	@Column(name = "banner_name")
	private String bannerName;
	
	@Column(name = "bannerImage")
	private String bannerImage;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "start_date")
	private Date startDate;
	
	@Column(name = "end_date")
	private Date endDate;

	public Long getBannerId() {
		return bannerId;
	}

	public void setBannerId(Long bannerId) {
		this.bannerId = bannerId;
	}

	public String getBannerName() {
		return bannerName;
	}

	public void setBannerName(String bannerName) {
		this.bannerName = bannerName;
	}

	public String getBannerImage() {
		return bannerImage;
	}

	public void setBannerImage(String bannerImage) {
		this.bannerImage = bannerImage;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return "LtAolBanners [bannerId=" + bannerId + ", bannerName=" + bannerName + ", bannerImage=" + bannerImage
				+ ", status=" + status + ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}
}
