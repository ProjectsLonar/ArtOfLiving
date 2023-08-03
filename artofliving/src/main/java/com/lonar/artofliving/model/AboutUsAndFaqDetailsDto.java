package com.lonar.artofliving.model;

import java.util.List;

public class AboutUsAndFaqDetailsDto {

	private LtMastAboutUs ltMastAboutUs;

	private List<LtMastFaq> ltMastFAQlist;

	public LtMastAboutUs getLtMastAboutUs() {
		return ltMastAboutUs;
	}

	public void setLtMastAboutUs(LtMastAboutUs ltMastAboutUs) {
		this.ltMastAboutUs = ltMastAboutUs;
	}

	public List<LtMastFaq> getLtMastFAQlist() {
		return ltMastFAQlist;
	}

	public void setLtMastFAQlist(List<LtMastFaq> ltMastFAQlist) {
		this.ltMastFAQlist = ltMastFAQlist;
	}
}
