package com.sttech.tvdownload.RestrofitResponses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class helpRes {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("help_instruction_english")
    @Expose
    private String helpInstructionEnglish;
    @SerializedName("help_instruction_spanish")
    @Expose
    private String helpInstructionSpanish;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHelpInstructionEnglish() {
        return helpInstructionEnglish;
    }

    public void setHelpInstructionEnglish(String helpInstructionEnglish) {
        this.helpInstructionEnglish = helpInstructionEnglish;
    }

    public String getHelpInstructionSpanish() {
        return helpInstructionSpanish;
    }

    public void setHelpInstructionSpanish(String helpInstructionSpanish) {
        this.helpInstructionSpanish = helpInstructionSpanish;
    }
}
