package com.convertools.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.object.MappingSqlQuery;

import java.sql.ResultSet;
import java.sql.SQLException;

//@NoArgsConstructor
@Setter
@Getter
public class OutPutData {

    private Integer 	id	;
    private String 	testno	;
    private String 	testitem	;
    private String 	itemtable	;
    private String 	sampleno	;
    private String 	operatorname	;
    private String 	curorder	;
    private String 	testcount	;
    private String 	maxload	;
    private String 	maxdistort	;
    private String 	maxstrength	;
    private String 	yieldupload	;
    private String 	yieldupstrength	;
    private String 	yieldload	;
    private String 	yieldstrength	;
    private String 	fpload	;
    private String 	fpstrength	;
    private String 	ftload	;
    private String 	ftstrength	;
    private String 	finallength	;
    private String 	finalrate	;
    private String 	finalshrink	;
    private String 	finaldia	;
    private String 	finalwidth	;
    private String 	finalthick	;
    private String 	finalborder	;
    private String 	elasticity	;
    private String 	duration	;
    private String 	maxspeed	;
    private String 	temperature	;
    private String 	humidity	;
    private String 	testtime	;
    private String 	finalposition	;
    private String 	finalstate	;
    private String 	bendresult	;
    private String 	motherlength	;
    private String 	motherweight	;
    private String 	orggaugelength	;
    private String 	extgaugelength	;
    private String 	dia	;
    private String 	span	;
    private String 	length	;
    private String 	width	;
    private String 	thickness	;
    private String 	border	;
    private String 	outdia	;
    private String 	innerdia	;
    private String 	area	;
    private String 	equipcode	;
    private String 	measurerange	;
    private String 	identifier	;
    private String 	category	;
    private String 	isfinished	;
    private String 	testid	;
    private String 	savefilename	;
    private String 	ctrlmode	;
    private String 	distancebeforetest	;
    private String 	distanceaftertest	;
    private String 	sampleinfo1	;
    private String 	sampleinfo2	;
    private String 	sampleinfo3	;
    private String 	sampleinfo4	;
    private String 	sampleinfo5	;
    private String 	sampleinfo6	;
    private String 	sampleinfo7	;
    private String 	sampleinfo8	;
    private String 	sampleinfo9	;
    private String 	sampleinfo10	;
    private String 	curvepicture	;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTestno() {
        return testno;
    }

    public void setTestno(String testno) {
        this.testno = testno;
    }

    public String getTestitem() {
        return testitem;
    }

    public void setTestitem(String testitem) {
        this.testitem = testitem;
    }

    public String getItemtable() {
        return itemtable;
    }

    public void setItemtable(String itemtable) {
        this.itemtable = itemtable;
    }

    public String getSampleno() {
        return sampleno;
    }

    public void setSampleno(String sampleno) {
        this.sampleno = sampleno;
    }

    public String getOperatorname() {
        return operatorname;
    }

    public void setOperatorname(String operatorname) {
        this.operatorname = operatorname;
    }

    public String getCurorder() {
        return curorder;
    }

    public void setCurorder(String curorder) {
        this.curorder = curorder;
    }

    public String getTestcount() {
        return testcount;
    }

    public void setTestcount(String testcount) {
        this.testcount = testcount;
    }

    public String getMaxload() {
        return maxload;
    }

    public void setMaxload(String maxload) {
        this.maxload = maxload;
    }

    public String getMaxdistort() {
        return maxdistort;
    }

    public void setMaxdistort(String maxdistort) {
        this.maxdistort = maxdistort;
    }

    public String getMaxstrength() {
        return maxstrength;
    }

    public void setMaxstrength(String maxstrength) {
        this.maxstrength = maxstrength;
    }

    public String getYieldupload() {
        return yieldupload;
    }

    public void setYieldupload(String yieldupload) {
        this.yieldupload = yieldupload;
    }

    public String getYieldupstrength() {
        return yieldupstrength;
    }

    public void setYieldupstrength(String yieldupstrength) {
        this.yieldupstrength = yieldupstrength;
    }

    public String getYieldload() {
        return yieldload;
    }

    public void setYieldload(String yieldload) {
        this.yieldload = yieldload;
    }

    public String getYieldstrength() {
        return yieldstrength;
    }

    public void setYieldstrength(String yieldstrength) {
        this.yieldstrength = yieldstrength;
    }

    public String getFpload() {
        return fpload;
    }

    public void setFpload(String fpload) {
        this.fpload = fpload;
    }

    public String getFpstrength() {
        return fpstrength;
    }

    public void setFpstrength(String fpstrength) {
        this.fpstrength = fpstrength;
    }

    public String getFtload() {
        return ftload;
    }

    public void setFtload(String ftload) {
        this.ftload = ftload;
    }

    public String getFtstrength() {
        return ftstrength;
    }

    public void setFtstrength(String ftstrength) {
        this.ftstrength = ftstrength;
    }

    public String getFinallength() {
        return finallength;
    }

    public void setFinallength(String finallength) {
        this.finallength = finallength;
    }

    public String getFinalrate() {
        return finalrate;
    }

    public void setFinalrate(String finalrate) {
        this.finalrate = finalrate;
    }

    public String getFinalshrink() {
        return finalshrink;
    }

    public void setFinalshrink(String finalshrink) {
        this.finalshrink = finalshrink;
    }

    public String getFinaldia() {
        return finaldia;
    }

    public void setFinaldia(String finaldia) {
        this.finaldia = finaldia;
    }

    public String getFinalwidth() {
        return finalwidth;
    }

    public void setFinalwidth(String finalwidth) {
        this.finalwidth = finalwidth;
    }

    public String getFinalthick() {
        return finalthick;
    }

    public void setFinalthick(String finalthick) {
        this.finalthick = finalthick;
    }

    public String getFinalborder() {
        return finalborder;
    }

    public void setFinalborder(String finalborder) {
        this.finalborder = finalborder;
    }

    public String getElasticity() {
        return elasticity;
    }

    public void setElasticity(String elasticity) {
        this.elasticity = elasticity;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getMaxspeed() {
        return maxspeed;
    }

    public void setMaxspeed(String maxspeed) {
        this.maxspeed = maxspeed;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getTesttime() {
        return testtime;
    }

    public void setTesttime(String testtime) {
        this.testtime = testtime;
    }

    public String getFinalposition() {
        return finalposition;
    }

    public void setFinalposition(String finalposition) {
        this.finalposition = finalposition;
    }

    public String getFinalstate() {
        return finalstate;
    }

    public void setFinalstate(String finalstate) {
        this.finalstate = finalstate;
    }

    public String getBendresult() {
        return bendresult;
    }

    public void setBendresult(String bendresult) {
        this.bendresult = bendresult;
    }

    public String getMotherlength() {
        return motherlength;
    }

    public void setMotherlength(String motherlength) {
        this.motherlength = motherlength;
    }

    public String getMotherweight() {
        return motherweight;
    }

    public void setMotherweight(String motherweight) {
        this.motherweight = motherweight;
    }

    public String getOrggaugelength() {
        return orggaugelength;
    }

    public void setOrggaugelength(String orggaugelength) {
        this.orggaugelength = orggaugelength;
    }

    public String getExtgaugelength() {
        return extgaugelength;
    }

    public void setExtgaugelength(String extgaugelength) {
        this.extgaugelength = extgaugelength;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getSpan() {
        return span;
    }

    public void setSpan(String span) {
        this.span = span;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getThickness() {
        return thickness;
    }

    public void setThickness(String thickness) {
        this.thickness = thickness;
    }

    public String getBorder() {
        return border;
    }

    public void setBorder(String border) {
        this.border = border;
    }

    public String getOutdia() {
        return outdia;
    }

    public void setOutdia(String outdia) {
        this.outdia = outdia;
    }

    public String getInnerdia() {
        return innerdia;
    }

    public void setInnerdia(String innerdia) {
        this.innerdia = innerdia;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getEquipcode() {
        return equipcode;
    }

    public void setEquipcode(String equipcode) {
        this.equipcode = equipcode;
    }

    public String getMeasurerange() {
        return measurerange;
    }

    public void setMeasurerange(String measurerange) {
        this.measurerange = measurerange;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getIsfinished() {
        return isfinished;
    }

    public void setIsfinished(String isfinished) {
        this.isfinished = isfinished;
    }

    public String getTestid() {
        return testid;
    }

    public void setTestid(String testid) {
        this.testid = testid;
    }

    public String getSavefilename() {
        return savefilename;
    }

    public void setSavefilename(String savefilename) {
        this.savefilename = savefilename;
    }

    public String getCtrlmode() {
        return ctrlmode;
    }

    public void setCtrlmode(String ctrlmode) {
        this.ctrlmode = ctrlmode;
    }

    public String getDistancebeforetest() {
        return distancebeforetest;
    }

    public void setDistancebeforetest(String distancebeforetest) {
        this.distancebeforetest = distancebeforetest;
    }

    public String getDistanceaftertest() {
        return distanceaftertest;
    }

    public void setDistanceaftertest(String distanceaftertest) {
        this.distanceaftertest = distanceaftertest;
    }

    public String getSampleinfo1() {
        return sampleinfo1;
    }

    public void setSampleinfo1(String sampleinfo1) {
        this.sampleinfo1 = sampleinfo1;
    }

    public String getSampleinfo2() {
        return sampleinfo2;
    }

    public void setSampleinfo2(String sampleinfo2) {
        this.sampleinfo2 = sampleinfo2;
    }

    public String getSampleinfo3() {
        return sampleinfo3;
    }

    public void setSampleinfo3(String sampleinfo3) {
        this.sampleinfo3 = sampleinfo3;
    }

    public String getSampleinfo4() {
        return sampleinfo4;
    }

    public void setSampleinfo4(String sampleinfo4) {
        this.sampleinfo4 = sampleinfo4;
    }

    public String getSampleinfo5() {
        return sampleinfo5;
    }

    public void setSampleinfo5(String sampleinfo5) {
        this.sampleinfo5 = sampleinfo5;
    }

    public String getSampleinfo6() {
        return sampleinfo6;
    }

    public void setSampleinfo6(String sampleinfo6) {
        this.sampleinfo6 = sampleinfo6;
    }

    public String getSampleinfo7() {
        return sampleinfo7;
    }

    public void setSampleinfo7(String sampleinfo7) {
        this.sampleinfo7 = sampleinfo7;
    }

    public String getSampleinfo8() {
        return sampleinfo8;
    }

    public void setSampleinfo8(String sampleinfo8) {
        this.sampleinfo8 = sampleinfo8;
    }

    public String getSampleinfo9() {
        return sampleinfo9;
    }

    public void setSampleinfo9(String sampleinfo9) {
        this.sampleinfo9 = sampleinfo9;
    }

    public String getSampleinfo10() {
        return sampleinfo10;
    }

    public void setSampleinfo10(String sampleinfo10) {
        this.sampleinfo10 = sampleinfo10;
    }

    public String getCurvepicture() {
        return curvepicture;
    }

    public void setCurvepicture(String curvepicture) {
        this.curvepicture = curvepicture;
    }

    public static final class QueryMapper extends MappingSqlQuery<OutPutData> {

        private final SingleColumnRowMapper<OutPutData> rowMapper = new SingleColumnRowMapper<>();

        @Override
        protected OutPutData mapRow(ResultSet rs, int rowNum) throws SQLException {
            return rowMapper.mapRow(rs, rowNum);
        }
    }
}
