package dto;

import java.sql.Timestamp;

/**
 * 2026-03-12
 * 김채영
 * Inquiry DTO 구현
 */
public class Inquiry {
    private int inquiryId;
    private int memberId;
    private String title;
    private String content;
    private String category;
    private Timestamp createdAt;
    private boolean processed;
    private String response;


    public Inquiry(){

    }

    public Inquiry(int inquiryId, int memberId, String title, String content, String category, Timestamp createdAt, boolean processed, String response) {
        this.inquiryId = inquiryId;
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.category = category;
        this.createdAt = createdAt;
        this.processed = processed;
        this.response = response;

    }
    public Inquiry(int inquiryId, int memberId, String title, String category, boolean processed) {
        this.inquiryId = inquiryId;
        this.memberId = memberId;
        this.title = title;
        this.category = category;
        this.processed = processed;
    }

    public int getInquiryId() {
        return inquiryId;
    }

    public void setInquiryId(int inquiryId) {
        this.inquiryId = inquiryId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public boolean getProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

}
