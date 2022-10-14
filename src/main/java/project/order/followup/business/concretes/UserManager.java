package project.order.followup.business.concretes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.order.followup.business.abstracts.UserService;
import project.order.followup.core.dataAccess.UserConnection;
import project.order.followup.core.entities.User;
import project.order.followup.core.utilities.results.DataResult;
import project.order.followup.core.utilities.results.ErrorResult;
import project.order.followup.core.utilities.results.Result;
import project.order.followup.core.utilities.results.SuccessDataResult;
import project.order.followup.core.utilities.results.SuccessResult;

@Service
public class UserManager implements UserService{
	
	private UserConnection userConnection;
	
	@Autowired
	public UserManager(UserConnection userConnection) {
		super();
		this.userConnection = userConnection;
	}

	@Override
	public Result add(User user) {
		user.setPresent(1);
		this.userConnection.save(user);
		return new SuccessResult("Kullanıcı Eklendi");
	}

	@Override
	public DataResult<User> findByEmail(String email) {
		return new SuccessDataResult<User>(this.userConnection.findByEmail(email),"Kullanıcı bulundu");
	}

	@Override
	public Result update(User user) {
		if(this.userConnection.findById(user.getId()).get().getPresent()==1) {
			if(user.getEmail().equals("string@a")) {
				user.setEmail(this.userConnection.findById(user.getId()).get().getEmail());
			}
			if(user.getName().equals("string")) {
				user.setName(this.userConnection.findById(user.getId()).get().getName());
			}
			if(user.getPhone().equals("string")) {
				user.setPhone(this.userConnection.findById(user.getId()).get().getPhone());
			}
			if(user.getPassword().equals("string")) {
				user.setPassword(this.userConnection.findById(user.getId()).get().getPassword());
			}
			user.setPresent(1);
			this.userConnection.save(user);
			return new SuccessResult("Kullanıcı Güncellendi");
		}else {
			if(user.getPresent()==0) {
				return new ErrorResult("Uyarı: Silinmiş Kişi!");
			}else {
				user.setEmail(this.userConnection.findById(user.getId()).get().getEmail());
				user.setName(this.userConnection.findById(user.getId()).get().getName());
				user.setPhone(this.userConnection.findById(user.getId()).get().getPhone());
				user.setPassword(this.userConnection.findById(user.getId()).get().getPassword());
				user.setPresent(1);
				this.userConnection.save(user);
				return new SuccessResult("Kullanıcı yeniden aktif!");
			}
		}
	}

	@Override
	public DataResult<List<User>> getAllUsers() {
		List<User> users = new ArrayList<User>();
		for(int i=0;i<this.userConnection.count();i++) {
			if(this.userConnection.findAll().get(i).getPresent()==1) {
				users.add(this.userConnection.findAll().get(i));
			}
			else {
				
			}
		}		
		return new SuccessDataResult<List<User>>(users,"Users Listelendi");
	
	}

	@Override
	public Result deleteByUserId(int id) {
		Optional<User> optionalUser  = this.userConnection.findById(id);
		if(optionalUser.isPresent()) {
			if(this.userConnection.findById(id).get().getPresent()==1) {
				optionalUser.get().setPresent(0);
				this.userConnection.save(optionalUser.get());
				return new SuccessResult("User Silindi");
			}else {
				return new ErrorResult("User zaten silinmiş!");
			}
			
		}else{
			return new ErrorResult("User Bulunamadı");
		}
			/*this.productConnection.deleteById(id);
			return new SuccessResult("Ürün silindi");*/
		
	}
	
}
