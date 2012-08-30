package sysSrc.common.gridTag;
import baseSrc.common.AutoArrayList;
import baseSrc.framework.BaseActionForm;

public class BPF010702Form extends BaseActionForm {
	
	private String cxny;
	private String cjbh;
	private String cjmc;
	private String allCount;
	private AutoArrayList BPF010702Details = new AutoArrayList(BPF010702Details.class);
	public AutoArrayList getBPF010702Details() {
		return BPF010702Details;
	}
	public void setBPF010702Details(AutoArrayList BPF010702Details) {
		this.BPF010702Details.clear();
		this.BPF010702Details = BPF010702Details;
	}
	
	public String getCxny() {
		return cxny;
	}
	public void setCxny(String cxny) {
		this.cxny = cxny;
	}
	public String getCjbh() {
		return cjbh;
	}
	public void setCjbh(String cjbh) {
		this.cjbh = cjbh;
	}
	public String getCjmc() {
		return cjmc;
	}
	public void setCjmc(String cjmc) {
		this.cjmc = cjmc;
	}
	public String getAllCount() {
		return allCount;
	}
	public void setAllCount(String allCount) {
		this.allCount = allCount;
	}
	
}