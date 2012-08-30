package demoSrc.orm;



/**
 * TIscsample entity. @author MyEclipse Persistence Tools
 */

public class TBasesample  implements java.io.Serializable {

	 private static final long serialVersionUID = 1L;
	 private Integer sysno;
	 private String name;
	 private String sex;
	 private String age;
	 private String city;
	 private String deadyear;
	 private Double money;

    // Constructors

    /** default constructor */
    public TBasesample() {
    }

	/** minimal constructor */
    public TBasesample(Integer sysno) {
        this.sysno = sysno;
    }
    
    /** full constructor */
    public TBasesample(Integer sysno, String name, String sex, String age, String city, String deadyear,Double money) {
        this.sysno = sysno;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.city = city;
        this.deadyear = deadyear;
        this.money = money;
    }

   
    // Property accessors

    public Integer getSysno() {
        return this.sysno;
    }
    
    public void setSysno(Integer sysno) {
        this.sysno = sysno;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return this.sex;
    }
    
    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return this.age;
    }
    
    public void setAge(String age) {
        this.age = age;
    }

    public String getCity() {
        return this.city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }

    public String getDeadyear() {
        return this.deadyear;
    }
    
    public void setDeadyear(String deadyear) {
        this.deadyear = deadyear;
    }
    
    public Double getMoney() {
		return money;
	}
    
	public void setMoney(Double money) {
		this.money = money;
	}
   








}