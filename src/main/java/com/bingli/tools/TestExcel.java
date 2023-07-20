package com.bingli.tools;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.write.metadata.WriteSheet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestExcel {
    public static void main(String[] args) throws CloneNotSupportedException, IOException {
        // kingbase
        String insert = "INSERT INTO hrmresource (id,loginid,\"password\",lastname,sex,birthday,nationality,systemlanguage,maritalstatus,telephone,mobile,mobilecall,email,locationid,workroom,homeaddress,resourcetype,startdate,enddate,jobtitle,jobactivitydesc,joblevel,seclevel,departmentid,subcompanyid1,costcenterid,managerid,assistantid,bankid1,accountid1,resourceimageid,createrid,createdate,lastmodid,lastmoddate,lastlogindate,datefield1,datefield2,datefield3,datefield4,datefield5,numberfield1,numberfield2,numberfield3,numberfield4,numberfield5,textfield1,textfield2,textfield3,textfield4,textfield5,tinyintfield1,tinyintfield2,tinyintfield3,tinyintfield4,tinyintfield5,certificatenum,nativeplace,educationlevel,bememberdate,bepartydate,workcode,regresidentplace,healthinfo,residentplace,\"policy\",\"degree\",height,usekind,jobcall,accumfundaccount,birthplace,folk,residentphone,residentpostcode,extphone,managerstr,status,fax,islabouunion,weight,tempresidentnumber,probationenddate,countryid,passwdchgdate,needusb,serial,account,lloginid,needdynapass,dsporder,passwordstate,accounttype,belongto,dactylogram,assistantdactylogram,passwordlock,sumpasswordwrong,oldpassword1,oldpassword2,msgstyle,messagerurl,pinyinlastname,tokenkey,userusbtype,outkey,adsjgs,adgs,adbm,mobileshowtype,usbstate,ecology_pinyin_search,isadaccount,accountname,haschangepwd,created,creater,modified,modifier,passwordlocktime,mobilecaflag,salt,companystartdate,workstartdate,secondarypwd,usesecondarypwd,classification,uuid,passwordlockreason,companyworkyear,workyear,dismissdate,enckey,crc,usbscope,tenant_key,clauthtype,hashdata,signdata) VALUES ";
        String template = " (##id##,'##loginid##','0TnV4JjIr0z60yM7vEakOcyjmkwW6573IchGRoMagEg=','##loginid##','1','2010-10-11',NULL,7,'2',NULL,'5654321','123456','',3,'4','xxxx',NULL,'2010-08-10','2018-08-10',3,'xxxx',9,10,8,5,NULL,0,0,70,'xxx',NULL,1,'2023-07-20',1,'2023-07-20','2023-07-20',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'##idcard##','湖北',0,'2010-08-08','2010-08-10','##number##','武汉','0','xxxx','党员','xxx','172',4,5,'xxx',NULL,'汉',NULL,NULL,NULL,'',1,NULL,'0',60,'xxxx','2010-11-10',1,NULL,NULL,NULL,NULL,NULL,NULL,82.0,NULL,0,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'##loginid##^##loginid##',NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,'##loginid##^##loginid##',NULL,'xxx',NULL,'2023-07-20 15:09:07.669592',1,'2023-07-20 15:09:07.669592',1,NULL,NULL,'sm3_new#8e835c0b-7670-498d-ab2e-1747a312f30e','2018-08-10','2018-08-10',NULL,NULL,'3','##uuid##',NULL,4.95,4.95,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);";
        // pgsql
//        String insert = "INSERT INTO hrmresource (id,loginid,\"password\",lastname,sex,birthday,nationality,systemlanguage,maritalstatus,telephone,mobile,mobilecall,email,locationid,workroom,homeaddress,resourcetype,startdate,enddate,jobtitle,jobactivitydesc,joblevel,seclevel,departmentid,subcompanyid1,costcenterid,managerid,assistantid,bankid1,accountid1,resourceimageid,createrid,createdate,lastmodid,lastmoddate,lastlogindate,datefield1,datefield2,datefield3,datefield4,datefield5,numberfield1,numberfield2,numberfield3,numberfield4,numberfield5,textfield1,textfield2,textfield3,textfield4,textfield5,tinyintfield1,tinyintfield2,tinyintfield3,tinyintfield4,tinyintfield5,certificatenum,nativeplace,educationlevel,bememberdate,bepartydate,workcode,regresidentplace,healthinfo,residentplace,\"policy\",\"degree\",height,usekind,jobcall,accumfundaccount,birthplace,folk,residentphone,residentpostcode,extphone,managerstr,status,fax,islabouunion,weight,tempresidentnumber,probationenddate,countryid,passwdchgdate,needusb,serial,account,lloginid,needdynapass,dsporder,passwordstate,accounttype,belongto,dactylogram,assistantdactylogram,passwordlock,sumpasswordwrong,oldpassword1,oldpassword2,msgstyle,messagerurl,pinyinlastname,tokenkey,userusbtype,outkey,adsjgs,adgs,adbm,mobileshowtype,usbstate,totalspace,occupyspace,ecology_pinyin_search,isadaccount,accountname,haschangepwd,created,creater,modified,modifier,passwordlocktime,mobilecaflag,salt,companystartdate,workstartdate,secondarypwd,usesecondarypwd,classification,uuid,passwordlockreason,companyworkyear,workyear,dismissdate,enckey,crc,usbscope,tenant_key,clauthtype,hashdata,signdata) VALUES ";
//        String template = " (##id##,'##loginid##','0TnV4JjIr0z60yM7vEakOcyjmkwW6573IchGRoMagEg=','##loginid##','1','2010-10-11',NULL,7,'2',NULL,'5654321','123456','',3,'4','xxxx',NULL,'2010-08-10','2018-08-10',3,'xxxx',9,10,8,5,NULL,0,0,70,'xxx',NULL,1,'2023-07-20',1,'2023-07-20','2023-07-20',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'##idcard##','湖北',0,'2010-08-08','2010-08-10','##number##','武汉','0','xxxx','党员','xxx','172',4,5,'xxx',NULL,'汉',NULL,NULL,NULL,'',1,NULL,'0',60,'xxxx','2010-11-10',1,NULL,NULL,NULL,NULL,NULL,NULL,82.0,NULL,0,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'##loginid##^##loginid##',NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,100.0,0.0,'##loginid##^##loginid##',NULL,'xxx',NULL,'2023-07-20 15:09:07.669592',1,'2023-07-20 15:09:07.669592',1,NULL,NULL,'sm3_new#8e835c0b-7670-498d-ab2e-1747a312f30e','2018-08-10','2018-08-10',NULL,NULL,'3','##uuid##',NULL,4.95,4.95,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);";
        int id = 0;
        String number = null;
        String loginid = null;
        String idcard = null;
        String uuid = null;

        String sqlfile = "hrmresource.sql";
        int numberOfUsernames = 500000;

        try (FileWriter writer = new FileWriter(sqlfile)) {
            for (int i = 0; i < numberOfUsernames; i++) {
                String value = template;
                id = i + 1;
                number = "fw"+i;
                loginid = "user" + i;
                idcard = String.valueOf(111111 + i);
                uuid = UUID.randomUUID().toString();
                value = value.replaceAll("##id##", id + "");
                value = value.replaceAll("##number##", number);
                value = value.replaceAll("##loginid##", loginid);
                value = value.replaceAll("##idcard##", idcard);
                value = value.replaceAll("##uuid##", uuid);
                writer.append(insert + value);
                writer.append("\n");

            }
            writer.flush();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void excel() throws CloneNotSupportedException, IOException {
        //实现excel写操作
        //1设置写入文件夹地址和excel文件名称
        String filename="C:\\Users\\bes\\Downloads\\50w.xls";

        List<UserData> users = new ArrayList<>();
        EasyExcel.read(filename).head(UserData.class).sheet("人员导入标准模板").registerReadListener(new AnalysisEventListener<UserData>() {
            @Override
            public void invoke(UserData o, AnalysisContext analysisContext) {
                users.add(o);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {

            }
        }).doRead();
        int count = 20;
        for (int i=0; i<count;i++) {
            String name = "C:\\Users\\bes\\Downloads\\50w" + i + ".xls";
            create(i, name, users);
        }
    }

    private static void create(int index, String filename, List<UserData> users) throws CloneNotSupportedException, IOException {
        List<UserData> usersWrite = new ArrayList<>();
        for (int i = 0; i< users.size(); i++) {
//            usersWrite.add(new UserData());
            usersWrite.add(users.get(i));
        }
        int numberOfUsernames = 500000;
        int count = 20;
        UserData template = users.get(users.size()-1);
        for (int i = numberOfUsernames / count * index; i < numberOfUsernames / count * (index + 1); i++) {
            UserData user = template.clone();
            user.setNumber("fw"+i);
            user.setUsername("user" + i);
            user.setLoginname("user" + i);
            user.setIdcard(String.valueOf(111111+i));
            usersWrite.add(user);
        }
        usersWrite.add(0, new UserData());
        //2调用easyExcel里面方法进行写操作
        //
        new File(filename).createNewFile();

        OutputStream outputStream = new FileOutputStream(filename);
        ExcelWriter excelWriter = EasyExcel.write(outputStream).build();

        WriteSheet writeSheet1 = EasyExcel.writerSheet(1,"sheet1").build();
        WriteSheet writeSheet2 = EasyExcel.writerSheet(2,"人员导入标准模板").build();
        excelWriter.write(new ArrayList<UserData>(), writeSheet1);
        excelWriter.write(usersWrite, writeSheet2);
        excelWriter.finish();
//        EasyExcel.write(filename).excelType(ExcelTypeEnum.XLS).sheet("人员导入标准模板").doWrite(usersWrite);
    }

}