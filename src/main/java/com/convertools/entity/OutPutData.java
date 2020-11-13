package com.convertools.entity;


import com.convertools.annotations.ConvertField;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;


public class OutPutData extends BaseEntity {

    /*自动编号*/

    public Integer 	id	;
    /*委托编号*/
    @ConvertField(name = "Ecoder", desc = "委托单号")
    public String 	testno	;
    // 所属试验项目代号
    public String 	testitem	;
    /*试验项目表名，与三和兼容*/
    public String 	itemtable	;


    /*操作员姓名*/
    @ConvertField(name = "SampleNo", desc = "试样编号")
    public String 	sampleno	;
    /*操作员姓名*/
    @ConvertField(name = "operators", desc = "操作员")
    public String 	operatorname	;
    public Integer 	curorder	;
    public Integer 	testcount	;

    @ConvertField(name = "Fm", desc = "最大力（kN）")
    public double 	maxload	;

    public double 	maxdistort	;
    public double 	maxstrength	;

    @ConvertField(name = "FeH", desc = "上屈服力（kN）")
    public double 	yieldupload	;

    public double 	yieldupstrength	;
    public double 	yieldload	;
    public double 	yieldstrength	;
    public double 	fpload	;
    public double 	fpstrength	;
    public double 	ftload	;
    public double 	ftstrength	;

    @ConvertField(name = "lu", desc = "断后标距（mm）")
    public double 	finallength	;
    public double 	finalrate	;
    public double 	finalshrink	;

    @ConvertField(name="du", desc = "断后直径")
    public double 	finaldia	;

    public double 	finalwidth	;
    public double 	finalthick	;
    public double 	finalborder	;
    public double 	elasticity	;
    public double 	duration	;
    public double 	maxspeed	;

    public double 	temperature	;
    public double 	humidity	;

    @ConvertField(name = "WorkTime", desc = "测试时间")
    public long 	testtime	;
    public String 	finalposition	;
    public String 	finalstate	;
    public String 	bendresult	;
    public double 	motherlength	;
    public double 	motherweight	;

    @ConvertField(name = "lo", desc = "原始标距 (Lo)")
    public double 	orggaugelength	;

    public Integer 	extgaugelength	;

    @ConvertField(name = "do", desc = "试样直径")
    public double 	dia	;

    public double 	span	;
    public double 	length	;
    public double 	width	;
    public double 	thickness	;
    public double 	border	;
    public double 	outdia	;
    public double 	innerdia	;
    public double 	area	;
    public String 	equipcode	;
    public Integer 	measurerange	;
    public String 	identifier	;
    public String 	category	;
    public Integer 	isfinished	;
    public Integer 	testid	;
    public String 	savefilename	;
    public String 	ctrlmode	;
    public String 	distancebeforetest	;
    public String 	distanceaftertest	;
    public String 	sampleinfo1	;
    public String 	sampleinfo2	;
    public String 	sampleinfo3	;
    public String 	sampleinfo4	;
    public String 	sampleinfo5	;
    public String 	sampleinfo6	;
    public String 	sampleinfo7	;
    public String 	sampleinfo8	;
    public String 	sampleinfo9	;
    public String 	sampleinfo10	;
    public byte[] 	curvepicture	;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getCurorder() {
        return curorder;
    }

    public void setCurorder(Integer curorder) {
        this.curorder = curorder;
    }

    public Integer getTestcount() {
        return testcount;
    }

    public void setTestcount(Integer testcount) {
        this.testcount = testcount;
    }

    public double getMaxload() {
        return maxload;
    }

    public void setMaxload(double maxload) {
        this.maxload = maxload;
    }

    public double getMaxdistort() {
        return maxdistort;
    }

    public void setMaxdistort(double maxdistort) {
        this.maxdistort = maxdistort;
    }

    public double getMaxstrength() {
        return maxstrength;
    }

    public void setMaxstrength(double maxstrength) {
        this.maxstrength = maxstrength;
    }

    public double getYieldupload() {
        return yieldupload;
    }

    public void setYieldupload(double yieldupload) {
        this.yieldupload = yieldupload;
    }

    public double getYieldupstrength() {
        return yieldupstrength;
    }

    public void setYieldupstrength(double yieldupstrength) {
        this.yieldupstrength = yieldupstrength;
    }

    public double getYieldload() {
        return yieldload;
    }

    public void setYieldload(double yieldload) {
        this.yieldload = yieldload;
    }

    public double getYieldstrength() {
        return yieldstrength;
    }

    public void setYieldstrength(double yieldstrength) {
        this.yieldstrength = yieldstrength;
    }

    public double getFpload() {
        return fpload;
    }

    public void setFpload(double fpload) {
        this.fpload = fpload;
    }

    public double getFpstrength() {
        return fpstrength;
    }

    public void setFpstrength(double fpstrength) {
        this.fpstrength = fpstrength;
    }

    public double getFtload() {
        return ftload;
    }

    public void setFtload(double ftload) {
        this.ftload = ftload;
    }

    public double getFtstrength() {
        return ftstrength;
    }

    public void setFtstrength(double ftstrength) {
        this.ftstrength = ftstrength;
    }

    public double getFinallength() {
        return finallength;
    }

    public void setFinallength(double finallength) {
        this.finallength = finallength;
    }

    public double getFinalrate() {
        return finalrate;
    }

    public void setFinalrate(double finalrate) {
        this.finalrate = finalrate;
    }

    public double getFinalshrink() {
        return finalshrink;
    }

    public void setFinalshrink(double finalshrink) {
        this.finalshrink = finalshrink;
    }

    public double getFinaldia() {
        return finaldia;
    }

    public void setFinaldia(double finaldia) {
        this.finaldia = finaldia;
    }

    public double getFinalwidth() {
        return finalwidth;
    }

    public void setFinalwidth(double finalwidth) {
        this.finalwidth = finalwidth;
    }

    public double getFinalthick() {
        return finalthick;
    }

    public void setFinalthick(double finalthick) {
        this.finalthick = finalthick;
    }

    public double getFinalborder() {
        return finalborder;
    }

    public void setFinalborder(double finalborder) {
        this.finalborder = finalborder;
    }

    public double getElasticity() {
        return elasticity;
    }

    public void setElasticity(double elasticity) {
        this.elasticity = elasticity;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public double getMaxspeed() {
        return maxspeed;
    }

    public void setMaxspeed(double maxspeed) {
        this.maxspeed = maxspeed;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public long getTesttime() {
        return testtime;
    }

    public void setTesttime(long testtime) {
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

    public double getMotherlength() {
        return motherlength;
    }

    public void setMotherlength(double motherlength) {
        this.motherlength = motherlength;
    }

    public double getMotherweight() {
        return motherweight;
    }

    public void setMotherweight(double motherweight) {
        this.motherweight = motherweight;
    }

    public double getOrggaugelength() {
        return orggaugelength;
    }

    public void setOrggaugelength(double orggaugelength) {
        this.orggaugelength = orggaugelength;
    }

    public Integer getExtgaugelength() {
        return extgaugelength;
    }

    public void setExtgaugelength(Integer extgaugelength) {
        this.extgaugelength = extgaugelength;
    }

    public double getDia() {
        return dia;
    }

    public void setDia(double dia) {
        this.dia = dia;
    }

    public double getSpan() {
        return span;
    }

    public void setSpan(double span) {
        this.span = span;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getThickness() {
        return thickness;
    }

    public void setThickness(double thickness) {
        this.thickness = thickness;
    }

    public double getBorder() {
        return border;
    }

    public void setBorder(double border) {
        this.border = border;
    }

    public double getOutdia() {
        return outdia;
    }

    public void setOutdia(double outdia) {
        this.outdia = outdia;
    }

    public double getInnerdia() {
        return innerdia;
    }

    public void setInnerdia(double innerdia) {
        this.innerdia = innerdia;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public String getEquipcode() {
        return equipcode;
    }

    public void setEquipcode(String equipcode) {
        this.equipcode = equipcode;
    }

    public Integer getMeasurerange() {
        return measurerange;
    }

    public void setMeasurerange(Integer measurerange) {
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

    public Integer getIsfinished() {
        return isfinished;
    }

    public void setIsfinished(Integer isfinished) {
        this.isfinished = isfinished;
    }

    public Integer getTestid() {
        return testid;
    }

    public void setTestid(Integer testid) {
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

    public byte[] getCurvepicture() {
        return curvepicture;
    }

    public void setCurvepicture(byte[] curvepicture) {
        this.curvepicture = curvepicture;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("OutPutData{");
        sb.append("id=").append(id);
        sb.append(", testno='").append(testno).append('\'');
        sb.append(", testitem='").append(testitem).append('\'');
        sb.append(", itemtable='").append(itemtable).append('\'');
        sb.append(", sampleno='").append(sampleno).append('\'');
        sb.append(", operatorname='").append(operatorname).append('\'');
        sb.append(", curorder=").append(curorder);
        sb.append(", testcount=").append(testcount);
        sb.append(", maxload=").append(maxload);
        sb.append(", maxdistort=").append(maxdistort);
        sb.append(", maxstrength=").append(maxstrength);
        sb.append(", yieldupload=").append(yieldupload);
        sb.append(", yieldupstrength=").append(yieldupstrength);
        sb.append(", yieldload=").append(yieldload);
        sb.append(", yieldstrength=").append(yieldstrength);
        sb.append(", fpload=").append(fpload);
        sb.append(", fpstrength=").append(fpstrength);
        sb.append(", ftload=").append(ftload);
        sb.append(", ftstrength=").append(ftstrength);
        sb.append(", finallength=").append(finallength);
        sb.append(", finalrate=").append(finalrate);
        sb.append(", finalshrink=").append(finalshrink);
        sb.append(", finaldia=").append(finaldia);
        sb.append(", finalwidth=").append(finalwidth);
        sb.append(", finalthick=").append(finalthick);
        sb.append(", finalborder=").append(finalborder);
        sb.append(", elasticity=").append(elasticity);
        sb.append(", duration=").append(duration);
        sb.append(", maxspeed=").append(maxspeed);
        sb.append(", temperature=").append(temperature);
        sb.append(", humidity=").append(humidity);
        sb.append(", testtime=").append(testtime);
        sb.append(", finalposition='").append(finalposition).append('\'');
        sb.append(", finalstate='").append(finalstate).append('\'');
        sb.append(", bendresult='").append(bendresult).append('\'');
        sb.append(", motherlength=").append(motherlength);
        sb.append(", motherweight=").append(motherweight);
        sb.append(", orggaugelength=").append(orggaugelength);
        sb.append(", extgaugelength=").append(extgaugelength);
        sb.append(", dia=").append(dia);
        sb.append(", span=").append(span);
        sb.append(", length=").append(length);
        sb.append(", width=").append(width);
        sb.append(", thickness=").append(thickness);
        sb.append(", border=").append(border);
        sb.append(", outdia=").append(outdia);
        sb.append(", innerdia=").append(innerdia);
        sb.append(", area=").append(area);
        sb.append(", equipcode='").append(equipcode).append('\'');
        sb.append(", measurerange=").append(measurerange);
        sb.append(", identifier='").append(identifier).append('\'');
        sb.append(", category='").append(category).append('\'');
        sb.append(", isfinished=").append(isfinished);
        sb.append(", testid=").append(testid);
        sb.append(", savefilename='").append(savefilename).append('\'');
        sb.append(", ctrlmode='").append(ctrlmode).append('\'');
        sb.append(", distancebeforetest='").append(distancebeforetest).append('\'');
        sb.append(", distanceaftertest='").append(distanceaftertest).append('\'');
        sb.append(", sampleinfo1='").append(sampleinfo1).append('\'');
        sb.append(", sampleinfo2='").append(sampleinfo2).append('\'');
        sb.append(", sampleinfo3='").append(sampleinfo3).append('\'');
        sb.append(", sampleinfo4='").append(sampleinfo4).append('\'');
        sb.append(", sampleinfo5='").append(sampleinfo5).append('\'');
        sb.append(", sampleinfo6='").append(sampleinfo6).append('\'');
        sb.append(", sampleinfo7='").append(sampleinfo7).append('\'');
        sb.append(", sampleinfo8='").append(sampleinfo8).append('\'');
        sb.append(", sampleinfo9='").append(sampleinfo9).append('\'');
        sb.append(", sampleinfo10='").append(sampleinfo10).append('\'');
        sb.append(", curvepicture=");
        if (curvepicture == null) sb.append("null");
        else {
            sb.append('[');
            for (int i = 0; i < curvepicture.length; ++i)
                sb.append(i == 0 ? "" : ", ").append(curvepicture[i]);
            sb.append(']');
        }
        sb.append('}');
        return sb.toString();
    }

    public static OutPutData createOutPutData(Map<String, Object> dataMap) {
        OutPutData outPutData = new OutPutData();
        Class<? extends OutPutData> aClass = outPutData.getClass();
        Set<String> fields = dataMap.keySet();
        if (!fields.isEmpty()) {
            for (String fieldName : fields) {
                Field field = null;
                try {
                    field = aClass.getField(fieldName.toLowerCase());
                    if (field != null) {
                        field.setAccessible(true);
                        Object o = dataMap.get(fieldName);
                        if (o instanceof Integer) {
                            field.set(outPutData,  o);
                        } else if (o instanceof String) {
                            field.set(outPutData,   o);
                        } else if (o instanceof Double) {
                            field.set(outPutData,   o);
                        }  else if (o instanceof BigDecimal) {
                            BigDecimal d = (BigDecimal) o;
                            field.set(outPutData,   d.doubleValue());
                        }
                       // field.set(outPutData, dataMap.get(fieldName));
                    }
                } catch (NoSuchFieldException  e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        return outPutData;
    }
}
