package demoSrc.orm;

import java.math.BigDecimal;
import java.util.Date;


/**
 * TTestL entity. @author MyEclipse Persistence Tools
 */

public class TTestL  implements java.io.Serializable {


    // Fields    
	private static final long serialVersionUID = 1L;
	private String FStuffapplyno;
     private String FChangereason;
     private String FSafetypeid;
     private String FApplytypeid;
     private BigDecimal FProduceuserid;
     private BigDecimal FParttypeid;
     private String FPartname;
     private String FPartnameja;
     private String FUse;
     private String FUseja;
     private String FSpecification;
     private String FUnitid;
     private String FPartno;
     private String FPriority;
     private String FOldpartno;
     private Double FDestineqty;
     private Integer FSuggestarrivecyc;
     private Double FSuggestleastqty;
     private String FSuggestsupplier;
     private String FSuggestmanufacturer;
     private Double FIntendinguseqty;
     private String FCartype;
     private String FChargecode;
     private String FManagecode;
     private String FMachinestuffno;
     private String FMachinestuffname;
     private Double FMachinestuffuseqty;
     private String FMachinestuffmanufacturer;
     private String FRemark;
     private BigDecimal FApplicant;
     private BigDecimal FApplydept;
     private Date FWritetime;
     private BigDecimal FProcessinstanceid;
     private Date FUsedeptsubmitdate;
     private Date FProducedeptsubmitdate;
     private Date FDecisionsupplierdate;
     private String FBrand;
     private String FProducingarea;
     private String FSuggeststatus;
     private String FStockdisclaim;
     private String FProducedisclaim;
     private String FUsedisclaim;
     private BigDecimal FMsdsattachmentid;
     private BigDecimal FUsedeptattachmentid;
     private Double FConfirmleastqty;
     private Integer FComfirmarrivecyc;
     private String FComfirmpartname;
     private String FComfirmfSpecification;
     private String FComfirmunit;
     private String FComfirmsupplier;
     private String FStuffstatus;
     private String FExportstatus;
     private Date FSafecheck;
     private BigDecimal FSafecheckuser;
     private String FExigency;
     private String FSafemind;
     private BigDecimal FSortid;
     private String FCuid;
     private String FCpid;
     private String FCip;
     private Date FUdatetime;
     private String FUuid;
     private String FUpid;
     private String FUip;
     private String FIssendattachment;
     private String FProduceoption;
     private Date FProducedispatchdate;
     private Date FStockdispatchdate;
     private String FQinputnum;
     private String FSafemindremark;
     private String FComfirmmanufacturer;
     private String FComfirmproducingarea;


    // Constructors

    /** default constructor */
    public TTestL() {
    }

	/** minimal constructor */
    public TTestL(String FStuffapplyno, String FCuid, String FCpid, String FCip) {
        this.FStuffapplyno = FStuffapplyno;
        this.FCuid = FCuid;
        this.FCpid = FCpid;
        this.FCip = FCip;
    }
    
    /** full constructor */
    public TTestL(String FStuffapplyno, String FChangereason, String FSafetypeid, String FApplytypeid, BigDecimal FProduceuserid, BigDecimal FParttypeid, String FPartname, String FPartnameja, String FUse, String FUseja, String FSpecification, String FUnitid, String FPartno, String FPriority, String FOldpartno, Double FDestineqty, Integer FSuggestarrivecyc, Double FSuggestleastqty, String FSuggestsupplier, String FSuggestmanufacturer, Double FIntendinguseqty, String FCartype, String FChargecode, String FManagecode, String FMachinestuffno, String FMachinestuffname, Double FMachinestuffuseqty, String FMachinestuffmanufacturer, String FRemark, BigDecimal FApplicant, BigDecimal FApplydept, Date FWritetime, BigDecimal FProcessinstanceid, Date FUsedeptsubmitdate, Date FProducedeptsubmitdate, Date FDecisionsupplierdate, String FBrand, String FProducingarea, String FSuggeststatus, String FStockdisclaim, String FProducedisclaim, String FUsedisclaim, BigDecimal FMsdsattachmentid, BigDecimal FUsedeptattachmentid, Double FConfirmleastqty, Integer FComfirmarrivecyc, String FComfirmpartname, String FComfirmfSpecification, String FComfirmunit, String FComfirmsupplier, String FStuffstatus, String FExportstatus, Date FSafecheck, BigDecimal FSafecheckuser, String FExigency, String FSafemind, BigDecimal FSortid, String FCuid, String FCpid, String FCip, Date FUdatetime, String FUuid, String FUpid, String FUip, String FIssendattachment, String FProduceoption, Date FProducedispatchdate, Date FStockdispatchdate, String FQinputnum, String FSafemindremark, String FComfirmmanufacturer, String FComfirmproducingarea) {
        this.FStuffapplyno = FStuffapplyno;
        this.FChangereason = FChangereason;
        this.FSafetypeid = FSafetypeid;
        this.FApplytypeid = FApplytypeid;
        this.FProduceuserid = FProduceuserid;
        this.FParttypeid = FParttypeid;
        this.FPartname = FPartname;
        this.FPartnameja = FPartnameja;
        this.FUse = FUse;
        this.FUseja = FUseja;
        this.FSpecification = FSpecification;
        this.FUnitid = FUnitid;
        this.FPartno = FPartno;
        this.FPriority = FPriority;
        this.FOldpartno = FOldpartno;
        this.FDestineqty = FDestineqty;
        this.FSuggestarrivecyc = FSuggestarrivecyc;
        this.FSuggestleastqty = FSuggestleastqty;
        this.FSuggestsupplier = FSuggestsupplier;
        this.FSuggestmanufacturer = FSuggestmanufacturer;
        this.FIntendinguseqty = FIntendinguseqty;
        this.FCartype = FCartype;
        this.FChargecode = FChargecode;
        this.FManagecode = FManagecode;
        this.FMachinestuffno = FMachinestuffno;
        this.FMachinestuffname = FMachinestuffname;
        this.FMachinestuffuseqty = FMachinestuffuseqty;
        this.FMachinestuffmanufacturer = FMachinestuffmanufacturer;
        this.FRemark = FRemark;
        this.FApplicant = FApplicant;
        this.FApplydept = FApplydept;
        this.FWritetime = FWritetime;
        this.FProcessinstanceid = FProcessinstanceid;
        this.FUsedeptsubmitdate = FUsedeptsubmitdate;
        this.FProducedeptsubmitdate = FProducedeptsubmitdate;
        this.FDecisionsupplierdate = FDecisionsupplierdate;
        this.FBrand = FBrand;
        this.FProducingarea = FProducingarea;
        this.FSuggeststatus = FSuggeststatus;
        this.FStockdisclaim = FStockdisclaim;
        this.FProducedisclaim = FProducedisclaim;
        this.FUsedisclaim = FUsedisclaim;
        this.FMsdsattachmentid = FMsdsattachmentid;
        this.FUsedeptattachmentid = FUsedeptattachmentid;
        this.FConfirmleastqty = FConfirmleastqty;
        this.FComfirmarrivecyc = FComfirmarrivecyc;
        this.FComfirmpartname = FComfirmpartname;
        this.FComfirmfSpecification = FComfirmfSpecification;
        this.FComfirmunit = FComfirmunit;
        this.FComfirmsupplier = FComfirmsupplier;
        this.FStuffstatus = FStuffstatus;
        this.FExportstatus = FExportstatus;
        this.FSafecheck = FSafecheck;
        this.FSafecheckuser = FSafecheckuser;
        this.FExigency = FExigency;
        this.FSafemind = FSafemind;
        this.FSortid = FSortid;
        this.FCuid = FCuid;
        this.FCpid = FCpid;
        this.FCip = FCip;
        this.FUdatetime = FUdatetime;
        this.FUuid = FUuid;
        this.FUpid = FUpid;
        this.FUip = FUip;
        this.FIssendattachment = FIssendattachment;
        this.FProduceoption = FProduceoption;
        this.FProducedispatchdate = FProducedispatchdate;
        this.FStockdispatchdate = FStockdispatchdate;
        this.FQinputnum = FQinputnum;
        this.FSafemindremark = FSafemindremark;
        this.FComfirmmanufacturer = FComfirmmanufacturer;
        this.FComfirmproducingarea = FComfirmproducingarea;
    }

   
    // Property accessors

    public String getFStuffapplyno() {
        return this.FStuffapplyno;
    }
    
    public void setFStuffapplyno(String FStuffapplyno) {
        this.FStuffapplyno = FStuffapplyno;
    }

    public String getFChangereason() {
        return this.FChangereason;
    }
    
    public void setFChangereason(String FChangereason) {
        this.FChangereason = FChangereason;
    }

    public String getFSafetypeid() {
        return this.FSafetypeid;
    }
    
    public void setFSafetypeid(String FSafetypeid) {
        this.FSafetypeid = FSafetypeid;
    }

    public String getFApplytypeid() {
        return this.FApplytypeid;
    }
    
    public void setFApplytypeid(String FApplytypeid) {
        this.FApplytypeid = FApplytypeid;
    }

    public BigDecimal getFProduceuserid() {
        return this.FProduceuserid;
    }
    
    public void setFProduceuserid(BigDecimal FProduceuserid) {
        this.FProduceuserid = FProduceuserid;
    }

    public BigDecimal getFParttypeid() {
        return this.FParttypeid;
    }
    
    public void setFParttypeid(BigDecimal FParttypeid) {
        this.FParttypeid = FParttypeid;
    }

    public String getFPartname() {
        return this.FPartname;
    }
    
    public void setFPartname(String FPartname) {
        this.FPartname = FPartname;
    }

    public String getFPartnameja() {
        return this.FPartnameja;
    }
    
    public void setFPartnameja(String FPartnameja) {
        this.FPartnameja = FPartnameja;
    }

    public String getFUse() {
        return this.FUse;
    }
    
    public void setFUse(String FUse) {
        this.FUse = FUse;
    }

    public String getFUseja() {
        return this.FUseja;
    }
    
    public void setFUseja(String FUseja) {
        this.FUseja = FUseja;
    }

    public String getFSpecification() {
        return this.FSpecification;
    }
    
    public void setFSpecification(String FSpecification) {
        this.FSpecification = FSpecification;
    }

    public String getFUnitid() {
        return this.FUnitid;
    }
    
    public void setFUnitid(String FUnitid) {
        this.FUnitid = FUnitid;
    }

    public String getFPartno() {
        return this.FPartno;
    }
    
    public void setFPartno(String FPartno) {
        this.FPartno = FPartno;
    }

    public String getFPriority() {
        return this.FPriority;
    }
    
    public void setFPriority(String FPriority) {
        this.FPriority = FPriority;
    }

    public String getFOldpartno() {
        return this.FOldpartno;
    }
    
    public void setFOldpartno(String FOldpartno) {
        this.FOldpartno = FOldpartno;
    }

    public Double getFDestineqty() {
        return this.FDestineqty;
    }
    
    public void setFDestineqty(Double FDestineqty) {
        this.FDestineqty = FDestineqty;
    }

    public Integer getFSuggestarrivecyc() {
        return this.FSuggestarrivecyc;
    }
    
    public void setFSuggestarrivecyc(Integer FSuggestarrivecyc) {
        this.FSuggestarrivecyc = FSuggestarrivecyc;
    }

    public Double getFSuggestleastqty() {
        return this.FSuggestleastqty;
    }
    
    public void setFSuggestleastqty(Double FSuggestleastqty) {
        this.FSuggestleastqty = FSuggestleastqty;
    }

    public String getFSuggestsupplier() {
        return this.FSuggestsupplier;
    }
    
    public void setFSuggestsupplier(String FSuggestsupplier) {
        this.FSuggestsupplier = FSuggestsupplier;
    }

    public String getFSuggestmanufacturer() {
        return this.FSuggestmanufacturer;
    }
    
    public void setFSuggestmanufacturer(String FSuggestmanufacturer) {
        this.FSuggestmanufacturer = FSuggestmanufacturer;
    }

    public Double getFIntendinguseqty() {
        return this.FIntendinguseqty;
    }
    
    public void setFIntendinguseqty(Double FIntendinguseqty) {
        this.FIntendinguseqty = FIntendinguseqty;
    }

    public String getFCartype() {
        return this.FCartype;
    }
    
    public void setFCartype(String FCartype) {
        this.FCartype = FCartype;
    }

    public String getFChargecode() {
        return this.FChargecode;
    }
    
    public void setFChargecode(String FChargecode) {
        this.FChargecode = FChargecode;
    }

    public String getFManagecode() {
        return this.FManagecode;
    }
    
    public void setFManagecode(String FManagecode) {
        this.FManagecode = FManagecode;
    }

    public String getFMachinestuffno() {
        return this.FMachinestuffno;
    }
    
    public void setFMachinestuffno(String FMachinestuffno) {
        this.FMachinestuffno = FMachinestuffno;
    }

    public String getFMachinestuffname() {
        return this.FMachinestuffname;
    }
    
    public void setFMachinestuffname(String FMachinestuffname) {
        this.FMachinestuffname = FMachinestuffname;
    }

    public Double getFMachinestuffuseqty() {
        return this.FMachinestuffuseqty;
    }
    
    public void setFMachinestuffuseqty(Double FMachinestuffuseqty) {
        this.FMachinestuffuseqty = FMachinestuffuseqty;
    }

    public String getFMachinestuffmanufacturer() {
        return this.FMachinestuffmanufacturer;
    }
    
    public void setFMachinestuffmanufacturer(String FMachinestuffmanufacturer) {
        this.FMachinestuffmanufacturer = FMachinestuffmanufacturer;
    }

    public String getFRemark() {
        return this.FRemark;
    }
    
    public void setFRemark(String FRemark) {
        this.FRemark = FRemark;
    }

    public BigDecimal getFApplicant() {
        return this.FApplicant;
    }
    
    public void setFApplicant(BigDecimal FApplicant) {
        this.FApplicant = FApplicant;
    }

    public BigDecimal getFApplydept() {
        return this.FApplydept;
    }
    
    public void setFApplydept(BigDecimal FApplydept) {
        this.FApplydept = FApplydept;
    }

    public Date getFWritetime() {
        return this.FWritetime;
    }
    
    public void setFWritetime(Date FWritetime) {
        this.FWritetime = FWritetime;
    }

    public BigDecimal getFProcessinstanceid() {
        return this.FProcessinstanceid;
    }
    
    public void setFProcessinstanceid(BigDecimal FProcessinstanceid) {
        this.FProcessinstanceid = FProcessinstanceid;
    }

    public Date getFUsedeptsubmitdate() {
        return this.FUsedeptsubmitdate;
    }
    
    public void setFUsedeptsubmitdate(Date FUsedeptsubmitdate) {
        this.FUsedeptsubmitdate = FUsedeptsubmitdate;
    }

    public Date getFProducedeptsubmitdate() {
        return this.FProducedeptsubmitdate;
    }
    
    public void setFProducedeptsubmitdate(Date FProducedeptsubmitdate) {
        this.FProducedeptsubmitdate = FProducedeptsubmitdate;
    }

    public Date getFDecisionsupplierdate() {
        return this.FDecisionsupplierdate;
    }
    
    public void setFDecisionsupplierdate(Date FDecisionsupplierdate) {
        this.FDecisionsupplierdate = FDecisionsupplierdate;
    }

    public String getFBrand() {
        return this.FBrand;
    }
    
    public void setFBrand(String FBrand) {
        this.FBrand = FBrand;
    }

    public String getFProducingarea() {
        return this.FProducingarea;
    }
    
    public void setFProducingarea(String FProducingarea) {
        this.FProducingarea = FProducingarea;
    }

    public String getFSuggeststatus() {
        return this.FSuggeststatus;
    }
    
    public void setFSuggeststatus(String FSuggeststatus) {
        this.FSuggeststatus = FSuggeststatus;
    }

    public String getFStockdisclaim() {
        return this.FStockdisclaim;
    }
    
    public void setFStockdisclaim(String FStockdisclaim) {
        this.FStockdisclaim = FStockdisclaim;
    }

    public String getFProducedisclaim() {
        return this.FProducedisclaim;
    }
    
    public void setFProducedisclaim(String FProducedisclaim) {
        this.FProducedisclaim = FProducedisclaim;
    }

    public String getFUsedisclaim() {
        return this.FUsedisclaim;
    }
    
    public void setFUsedisclaim(String FUsedisclaim) {
        this.FUsedisclaim = FUsedisclaim;
    }

    public BigDecimal getFMsdsattachmentid() {
        return this.FMsdsattachmentid;
    }
    
    public void setFMsdsattachmentid(BigDecimal FMsdsattachmentid) {
        this.FMsdsattachmentid = FMsdsattachmentid;
    }

    public BigDecimal getFUsedeptattachmentid() {
        return this.FUsedeptattachmentid;
    }
    
    public void setFUsedeptattachmentid(BigDecimal FUsedeptattachmentid) {
        this.FUsedeptattachmentid = FUsedeptattachmentid;
    }

    public Double getFConfirmleastqty() {
        return this.FConfirmleastqty;
    }
    
    public void setFConfirmleastqty(Double FConfirmleastqty) {
        this.FConfirmleastqty = FConfirmleastqty;
    }

    public Integer getFComfirmarrivecyc() {
        return this.FComfirmarrivecyc;
    }
    
    public void setFComfirmarrivecyc(Integer FComfirmarrivecyc) {
        this.FComfirmarrivecyc = FComfirmarrivecyc;
    }

    public String getFComfirmpartname() {
        return this.FComfirmpartname;
    }
    
    public void setFComfirmpartname(String FComfirmpartname) {
        this.FComfirmpartname = FComfirmpartname;
    }

    public String getFComfirmfSpecification() {
        return this.FComfirmfSpecification;
    }
    
    public void setFComfirmfSpecification(String FComfirmfSpecification) {
        this.FComfirmfSpecification = FComfirmfSpecification;
    }

    public String getFComfirmunit() {
        return this.FComfirmunit;
    }
    
    public void setFComfirmunit(String FComfirmunit) {
        this.FComfirmunit = FComfirmunit;
    }

    public String getFComfirmsupplier() {
        return this.FComfirmsupplier;
    }
    
    public void setFComfirmsupplier(String FComfirmsupplier) {
        this.FComfirmsupplier = FComfirmsupplier;
    }

    public String getFStuffstatus() {
        return this.FStuffstatus;
    }
    
    public void setFStuffstatus(String FStuffstatus) {
        this.FStuffstatus = FStuffstatus;
    }

    public String getFExportstatus() {
        return this.FExportstatus;
    }
    
    public void setFExportstatus(String FExportstatus) {
        this.FExportstatus = FExportstatus;
    }

    public Date getFSafecheck() {
        return this.FSafecheck;
    }
    
    public void setFSafecheck(Date FSafecheck) {
        this.FSafecheck = FSafecheck;
    }

    public BigDecimal getFSafecheckuser() {
        return this.FSafecheckuser;
    }
    
    public void setFSafecheckuser(BigDecimal FSafecheckuser) {
        this.FSafecheckuser = FSafecheckuser;
    }

    public String getFExigency() {
        return this.FExigency;
    }
    
    public void setFExigency(String FExigency) {
        this.FExigency = FExigency;
    }

    public String getFSafemind() {
        return this.FSafemind;
    }
    
    public void setFSafemind(String FSafemind) {
        this.FSafemind = FSafemind;
    }

    public BigDecimal getFSortid() {
        return this.FSortid;
    }
    
    public void setFSortid(BigDecimal FSortid) {
        this.FSortid = FSortid;
    }

    public String getFCuid() {
        return this.FCuid;
    }
    
    public void setFCuid(String FCuid) {
        this.FCuid = FCuid;
    }

    public String getFCpid() {
        return this.FCpid;
    }
    
    public void setFCpid(String FCpid) {
        this.FCpid = FCpid;
    }

    public String getFCip() {
        return this.FCip;
    }
    
    public void setFCip(String FCip) {
        this.FCip = FCip;
    }

    public Date getFUdatetime() {
        return this.FUdatetime;
    }
    
    public void setFUdatetime(Date FUdatetime) {
        this.FUdatetime = FUdatetime;
    }

    public String getFUuid() {
        return this.FUuid;
    }
    
    public void setFUuid(String FUuid) {
        this.FUuid = FUuid;
    }

    public String getFUpid() {
        return this.FUpid;
    }
    
    public void setFUpid(String FUpid) {
        this.FUpid = FUpid;
    }

    public String getFUip() {
        return this.FUip;
    }
    
    public void setFUip(String FUip) {
        this.FUip = FUip;
    }

    public String getFIssendattachment() {
        return this.FIssendattachment;
    }
    
    public void setFIssendattachment(String FIssendattachment) {
        this.FIssendattachment = FIssendattachment;
    }

    public String getFProduceoption() {
        return this.FProduceoption;
    }
    
    public void setFProduceoption(String FProduceoption) {
        this.FProduceoption = FProduceoption;
    }

    public Date getFProducedispatchdate() {
        return this.FProducedispatchdate;
    }
    
    public void setFProducedispatchdate(Date FProducedispatchdate) {
        this.FProducedispatchdate = FProducedispatchdate;
    }

    public Date getFStockdispatchdate() {
        return this.FStockdispatchdate;
    }
    
    public void setFStockdispatchdate(Date FStockdispatchdate) {
        this.FStockdispatchdate = FStockdispatchdate;
    }

    public String getFQinputnum() {
        return this.FQinputnum;
    }
    
    public void setFQinputnum(String FQinputnum) {
        this.FQinputnum = FQinputnum;
    }

    public String getFSafemindremark() {
        return this.FSafemindremark;
    }
    
    public void setFSafemindremark(String FSafemindremark) {
        this.FSafemindremark = FSafemindremark;
    }

    public String getFComfirmmanufacturer() {
        return this.FComfirmmanufacturer;
    }
    
    public void setFComfirmmanufacturer(String FComfirmmanufacturer) {
        this.FComfirmmanufacturer = FComfirmmanufacturer;
    }

    public String getFComfirmproducingarea() {
        return this.FComfirmproducingarea;
    }
    
    public void setFComfirmproducingarea(String FComfirmproducingarea) {
        this.FComfirmproducingarea = FComfirmproducingarea;
    }
   








}