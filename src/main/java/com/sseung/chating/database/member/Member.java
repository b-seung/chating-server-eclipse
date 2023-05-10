package com.sseung.chating.database.member;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

//lombok 어노테이션
@Getter
@NoArgsConstructor
//JPA 어노테이션
@Entity
@Table(name="member")
public class Member {

	@Id
	@Column
	private String id;

	@Column
	private String password;

	@Column
	private String nickname;

	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthday;

	@Builder
	public Member(String id, String password, String nickname, LocalDate birthday) {
	    this.id = id;
	    this.password = password;
	    this.nickname = nickname;
	    this.birthday = birthday;
	}
}