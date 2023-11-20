package com.sh.address.view;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

import com.sh.address.controller.AddressController;
import com.sh.address.model.entity.Address;
import com.sh.address.model.vo.MemberAddress;
import com.sh.member.model.entity.Member;


public class AddressMenu {
	private Scanner sc = new Scanner(System.in);
	private AddressController addressController = new AddressController();

	public void mainMenu() {
		String menu = """
			***** 주소록 관리 *****
			1. 주소록 전체 조회
			2. 특정회원 주소록 조회
			3. 주소 추가
			4. 기본 주소지 수정
			5. 주소 삭제
			0. 이전 메뉴로 돌아가기
			********************	
			선택 : """;
		while(true) {
			System.out.println();
			System.out.print(menu);
			String choice = sc.next();
			
			List<Address> addresses = null;
			MemberAddress addr = null;
			switch(choice) {
			case "1" : 
				addresses = addressController.findAll();
				displayAddresses(addresses);
				break;
			case "2" : 
				addr = addressController.findByMemberId(inputMemberId());
				displayMemberAddress(addr);
				break;
			case "3" : 
				insertAddressMenu(inputMemberId());
				break;
			case "4" : 
				updateDefaultAddressMenu(inputMemberId());
				break;
			case "5" : 
				deleteAddressMenu(inputMemberId());
				break;
			case "0" : return;
			default :
				System.out.println("> 잘못 입력하셨습니다.");
				break;
			}
			
		}
		
	}
	private void updateDefaultAddressMenu(String memberId) {
		while (true) {
			// 1. 회원 아이디로 주소지 목록 조회
			MemberAddress member = addressController.findByMemberId(memberId);
			List<Address> addresses = member.getAddresses();
			if (addresses.isEmpty()) {
				System.out.println("> 회원 주소를 먼저 등록하세요.");
				return;
			} else if (addresses.size() == 1) {
				System.out.println("> 주소지가 하나뿐입니다.");
				return;
			}
			// 2. 기본 주소지 선택 및 수정
			System.out.println("> 다음중 기본 배송지를 선택하세요.");
			System.out.println("*********************************");
			for (int i = 0; i < addresses.size(); i++) {
				System.out.print((i + 1) + ". " + addresses.get(i).getAddress());
				System.out.println(addresses.get(i).isDefaultAddr() ? "(기본)" : "");
			}
			System.out.println("0. 이전 메뉴로 돌아가기");
			System.out.println("*********************************");
			System.out.print("선택 : ");
			int choice = sc.nextInt();
			// 0번 돌아가기
			// 1번~ 순번을 선택한 경우, 해당 인덱스에 해당하는 address_id 가져와서 수정
			if (choice == 0) {
				return;
			} else {
				// 수정
				int index = choice - 1;
				Address newDefaultAddress = addresses.get(index);
				int result = addressController.updateDefaultAddress(newDefaultAddress);
//				System.out.println(result);
				// update address set default_addr = 0 where member_id = ?
				// update address set default_addr = 1 where id = ?
			} 
		}
	}
//	private void displayMemberAddresses(MemberAddress member) {
//		System.out.println(member);
//		
//	}
	private String inputMemberId() {
		System.out.print("> 조회할 회원 아이디 : ");
		return sc.next();
	}
	private void displayMemberAddress(MemberAddress member) {
		if(member == null) {
			System.out.println("> 조회된 회원이 없습니다.");
		}
		else {
			System.out.println("---------------------------------");
			System.out.printf("ID : %s\n", member.getId());
			System.out.printf("Name : %s\n", member.getName());
			System.out.printf("Gender : %s\n", member.getGender());
			System.out.printf("Birthday : %s\n", member.getBirthday());
			System.out.printf("Email : %s\n", member.getEmail());
			System.out.printf("Point : %s\n", member.getPoint());
			System.out.printf("CreatedAt : %s\n", new SimpleDateFormat("yyyy-MM-dd HH:mm").format(member.getPoint()));			
			System.out.println("---------------------------------");
			
			List<Address> addresses = member.getAddresses();
			if(!addresses.isEmpty()) {
				for(Address addr : addresses) {
					System.out.println(
							"Address : " + addr.getAddress()
							+ (addr.isDefaultAddr() ? "(기본)" : "")
							);
				}
			}
		}
	}
	/**
     * n건의 주소조회 결과를 출력
     * @param members
     */
    private void displayAddresses(List<Address> addresses) {
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.printf("%-10s%-10s%-30s%-20s%-20s\n", 
                "ID", "MemberId", "Address", "DefaultAddr", "CreatedAt");
        System.out.println("--------------------------------------------------------------------------------------");
        if(addresses == null || addresses.isEmpty()) {
            System.out.println("\t\t 조회된 내용이 없습니다.");
        }
        else {
            for(Address address : addresses) {
                System.out.printf("%-10s%-10s%-20s%-30s%-20s\n", 
                        address.getId(), 
                        address.getMemberId(), 
                        address.getAddress(), 
                        address.isDefaultAddr(), 
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(address.getCreatedAt()));
            }
        }
        System.out.println("--------------------------------------------------------------------------------------");
    }
    
    private void insertAddressMenu(String memberId) {
		String address = null;
		boolean defaultAddr = false;
		// 1. 회원아이디로 주소지 목록 조회
		MemberAddress member = addressController.findByMemberId(memberId);
		List<Address> addresses = member.getAddresses();

		// 등록된 주소가 없다면 기본주소로 등록
		if (addresses.isEmpty()) {
			defaultAddr = true;
		} 
		// 2. 주소등록
		System.out.print("> 주소 입력 : ");
		sc.nextLine(); // 버퍼 개행제거용
		address = sc.nextLine();
		Address newAddress = new Address(0, memberId, address, defaultAddr, null);
		int result = addressController.insertAddress(newAddress);
		displayResult("주소 등록", result);
	}

	private void deleteAddressMenu(String memberId) {
		int result = 0;
		while (true) {
			// 1. 회원아이디로 주소지 목록 조회
			MemberAddress member = addressController.findByMemberId(memberId);
			List<Address> addresses = member.getAddresses();
			if (addresses.isEmpty()) {
				System.out.println("> 등록된 주소가 없습니다.");
				return;
			} else if (addresses.size() == 1) {
				result = addressController.deleteAddress(addresses.get(0).getId());
				displayResult("주소 삭제", result);
				return;
			}
			// 2. 기본주소지 선택 및 삭제
			System.out.println("> 다음중 삭제할 주소지를 선택하세요.(기본주소는 삭제할 수 없습니다.)");
			System.out.println("****************************");
			for (int i = 0; i < addresses.size(); i++) {
				System.out.print((i + 1) + ". " + addresses.get(i).getAddress());
				System.out.println(addresses.get(i).isDefaultAddr() ? "(기본)" : "");
			}
			System.out.println("0. 이전메뉴로 돌아가기");
			System.out.println("****************************");
			System.out.print("선택 : ");
			int choice = sc.nextInt();
			// 0번 돌아가기
			// 1번~ 순번을 선택한 경우, 해당인덱스에 해당하는 address_id 가져와서 수정
			if (choice == 0) {
				return;
			} 
			else if(choice >= 1 && choice <= addresses.size()){
				// 수정
				int index = choice - 1;
				Address delAddress = addresses.get(index);
				if(!delAddress.isDefaultAddr()) {
					result = addressController.deleteAddress(delAddress.getId());
					displayResult("주소 삭제", result);
					return;					
				}
				else {
					System.out.println("> 기본 주소는 삭제할 수 없습니다.");
				}
			} 
			else {
				System.out.println("> 잘못 입력했습니다.");
			}
		}
	}
	
	/**
	 * DML 처리결과 메소드
	 * @param type
	 * @param result
	 */
	private void displayResult(String type, int result) {
		if(result > 0)
			System.out.println("🎉🎉 " + type + " 성공! 🎉🎉");
		else
			System.out.println("😭😭 " + type + " 실패! 😭😭");				
	}
}
