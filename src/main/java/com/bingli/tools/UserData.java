package com.bingli.tools;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class UserData implements Cloneable{
    //设置excel表头名称
    @ExcelProperty("分部")
    private String divisional;
    @ExcelProperty("部门")
    private String departments;
    @ExcelProperty("编号")
    private String number;
    @ExcelProperty("姓名")
    private String username;
    @ExcelProperty("登录名")
    private String loginname;
    @ExcelProperty("密码")
    private String password;
    @ExcelProperty("账号类型")
    private String accounttype;
    @ExcelProperty("主账号")
    private String mainaccount;
    @ExcelProperty("性别")
    private String gender;
    @ExcelProperty("安全级别")
    private String securitylevel;
    @ExcelProperty("岗位")
    private String station;
    @ExcelProperty("职务")
    private String job;
    @ExcelProperty("职务类型")
    private String jobtype;
    @ExcelProperty("职称")
    private String title;
    @ExcelProperty("职级")
    private String rank;
    @ExcelProperty("职责描述")
    private String responsibility;
    @ExcelProperty("直接上级")
    private String directsupervisor;
    @ExcelProperty("助理")
    private String assistant;
    @ExcelProperty("状态")
    private String status;
    @ExcelProperty("办公室")
    private String office;
    @ExcelProperty("办公地点")
    private String officelocation;
    @ExcelProperty("办公电话")
    private String officephone;
    @ExcelProperty("移动电话")
    private String mobilephone;
    @ExcelProperty("其他电话")
    private String otherphone;
    @ExcelProperty("传真")
    private String fax;
    @ExcelProperty("电子邮件")
    private String email;
    @ExcelProperty("系统语言")
    private String systemlaunage;
    @ExcelProperty("出生日期")
    private String birthday;
    @ExcelProperty("民族")
    private String nation;
    @ExcelProperty("籍贯")
    private String nativeplace;
    @ExcelProperty("户口")
    private String household;
    @ExcelProperty("身份证号码")
    private String idcard;
    @ExcelProperty("婚姻状况")
    private String marital;
    @ExcelProperty("政治面貌")
    private String politicalOutlook ;
    @ExcelProperty("入团日期")
    private String joiningLeagueDate;
    @ExcelProperty("入党日期")
    private String joiningPartyDate;
    @ExcelProperty("工会会员")
    private String unionmember ;
    @ExcelProperty("学历")
    private String education;
    @ExcelProperty("学位")
    private String degree;
    @ExcelProperty("健康状况")
    private String healthcondition;
    @ExcelProperty("身高")
    private String height;
    @ExcelProperty("体重")
    private String weight;
    @ExcelProperty("用工性质")
    private String employmentnature;
    @ExcelProperty("合同开始日期")
    private String contractStartDate;
    @ExcelProperty("合同结束日期")
    private String contractEndDate;
    @ExcelProperty("试用期结束日期")
    private String trialPeriodEndDate;
    @ExcelProperty("入职日期")
    private String onboardingdate;
    @ExcelProperty("参加工作日期")
    private String employmentDate;
    @ExcelProperty("现居住地")
    private String currentResidence;
    @ExcelProperty("家庭联系方式")
    private String familyContactInformation;
    @ExcelProperty("暂住证号码")
    private String temporaryResidencePermitNumber;
    @ExcelProperty("工资账号户名")
    private String salaryAccountName;
    @ExcelProperty("工资银行")
    private String payrollBank;
    @ExcelProperty("工资账号")
    private String salaryAccount;
    @ExcelProperty("公积金帐户")
    private String providentFundAccount;
    @ExcelProperty("显示顺序")
    private String order;

    @Override
    protected UserData clone() throws CloneNotSupportedException {
        return (UserData) super.clone();
    }
}
