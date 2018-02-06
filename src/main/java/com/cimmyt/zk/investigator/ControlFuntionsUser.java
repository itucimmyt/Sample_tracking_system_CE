/*
Copyright 2013 International Maize and Wheat Improvement Center
   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
       http://www.apache.org/licenses/LICENSE-2.0
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package com.cimmyt.zk.investigator;

import static com.cimmyt.utils.Constants.LOCALE_LANGUAGE;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Window;

import com.cimmyt.utils.Constants;
import com.cimmyt.utils.ConstantsDNA;
import com.cimmyt.utils.PropertyHelper;

public class ControlFuntionsUser extends Window {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Checkbox id1;
	private Checkbox id2;
	private Checkbox id3;
	private Checkbox id4;
	private Checkbox id5;
	private Checkbox id6;
	private Checkbox id7;
	private Checkbox id8;
	private Checkbox id9;
	private Checkbox id10;
	private Checkbox id11;
	private Checkbox id12;
	private Checkbox id13;
	private Checkbox id14;
	private Checkbox id15;
	private Checkbox id16;
	private Checkbox id17;
	private Checkbox id18;
	private Checkbox id19;
	private Checkbox id20;
	private Checkbox id21;
	private Checkbox id22;
	private Checkbox id23;
	private Checkbox id24;
	private Checkbox id25;
	private Checkbox id26;
	private Checkbox id27;
	private Checkbox id28;
	private Checkbox id29;
	private Checkbox id30;
	private Checkbox id31;
	private Checkbox id32;
	private Checkbox id33;
	private Checkbox id34;
	private Checkbox id35;
	private Checkbox id36;
	private Checkbox id37;
	private Checkbox id38;
	private Checkbox id39;
	private Checkbox id40;
	private Checkbox id41;
	private Checkbox id42;
	private Checkbox id43;
	private Checkbox id44;
	private Checkbox id45;
	
	private PropertyHelper pro=null;
	private int roleFunctions;

	private Map <Integer, Integer> mapFunctions = new HashMap<Integer,Integer>();

	private Window idWindowFuntions;
	
	@SuppressWarnings("unchecked")
	public void loadContext(){
		initChecks();
		pro = (PropertyHelper)getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
		roleFunctions =(int) getDesktop().getSession().getAttribute(Constants.ATTRIBUTE_RESEARCHER_ROLE_FUNCTION);
		if (getDesktop().getSession().getAttribute(Constants.ATTRIBUTE_RESEARCHER_ROLE_MAP_FUNCTION) != null){
			clearSelection();
			mapFunctions = (Map<Integer,Integer>)getDesktop().getSession().getAttribute(Constants.ATTRIBUTE_RESEARCHER_ROLE_MAP_FUNCTION);
			setCheckboxWirtMap();
		}else{
			loadMap();
			checkCheckBYRole(roleFunctions);
		}
	}

	private void setCheckboxWirtMap (){
		if (mapFunctions != null && mapFunctions.size() > 0 ){
			String idStr = "id";
			Iterator<Integer> iterator = mapFunctions.keySet().iterator();
			while (iterator.hasNext()){
				Integer id = (Integer) iterator.next();
				Checkbox idCh =  (Checkbox)getFellow(idStr+id.intValue());
				idCh.setChecked(true);
			}
		}
	}
	private void clearSelection (){
		for (int i =1 ; i <= 45; i++){
			String id = "id"+i;
			Checkbox box = (Checkbox)getFellow(id);
			box.setChecked(false);
		}
	}

	public void add (){
		getDesktop().getSession().setAttribute(Constants.ATTRIBUTE_RESEARCHER_ROLE_MAP_FUNCTION, mapFunctions);
		idWindowFuntions.onClose();
	}

	public void cancel(){
		idWindowFuntions.onClose();
	}
	public void checkItem (Checkbox item){
		boolean isCheck = item.isChecked();
		int id =  Integer.parseInt(item.getValue().toString());
		switch (id){
			case 1 :
				checkGroupStudyTem(isCheck);
				break;
			case 5:
				chechGroupStoreLoc(isCheck);
				break;
			case 11 :
				chechGroupStudies(isCheck);
				break;
			case 18:
				chechGroupCompa(isCheck);
				break;
			case 22 :
				chechGroupShipment(isCheck);
				break;
			case 26:
				chechGroupProject(isCheck);
				break;
			case 30:
				chechGroupResearcher(isCheck);
				break;
			case 34 :
				chechGroupTiss(isCheck);
				break;
			case 38:
				chechGroupLocat(isCheck);
				break;
			case 42 :
				chechGroupSeason(isCheck);
				break;
			default:
				if (isCheck)
					mapFunctions.put(id, id);
				else
					mapFunctions.remove(id);
				break;
		}
	}

	private void chechGroupSeason (boolean isCheck){
		id42.setChecked(isCheck);
		id43.setChecked(isCheck);
		id44.setChecked(isCheck);
		id45.setChecked(isCheck);
		if (isCheck){
			mapFunctions.put(42, 42);
			mapFunctions.put(43, 43);
			mapFunctions.put(44, 44);
			mapFunctions.put(45, 45);
		}else {
			mapFunctions.remove(42);
			mapFunctions.remove(43);
			mapFunctions.remove(44);
			mapFunctions.remove(45);
		}
	}

	private void chechGroupLocat (boolean isCheck){
		id38.setChecked(isCheck);
		id39.setChecked(isCheck);
		id40.setChecked(isCheck);
		id41.setChecked(isCheck);
		if (isCheck){
			mapFunctions.put(38, 38);
			mapFunctions.put(39, 39);
			mapFunctions.put(40, 40);
			mapFunctions.put(41, 41);
		}else {
			mapFunctions.remove(38);
			mapFunctions.remove(39);
			mapFunctions.remove(40);
			mapFunctions.remove(41);
		}
	}

	private void chechGroupTiss (boolean isCheck){
		id34.setChecked(isCheck);
		id35.setChecked(isCheck);
		id36.setChecked(isCheck);
		id37.setChecked(isCheck);
		if (isCheck){
			mapFunctions.put(34, 34);
			mapFunctions.put(35, 35);
			mapFunctions.put(36, 36);
			mapFunctions.put(37, 37);
		}else {
			mapFunctions.remove(34);
			mapFunctions.remove(35);
			mapFunctions.remove(36);
			mapFunctions.remove(37);
		}
	}

	private void chechGroupResearcher (boolean isCheck){
		id30.setChecked(isCheck);
		id31.setChecked(isCheck);
		id32.setChecked(isCheck);
		id33.setChecked(isCheck);
		if (isCheck){
			mapFunctions.put(30, 30);
			mapFunctions.put(31, 31);
			mapFunctions.put(32, 32);
			mapFunctions.put(33, 33);
		}else {
			mapFunctions.remove(30);
			mapFunctions.remove(31);
			mapFunctions.remove(32);
			mapFunctions.remove(33);
		}
	}
	private void chechGroupProject (boolean isCheck){
		id26.setChecked(isCheck);
		id27.setChecked(isCheck);
		id28.setChecked(isCheck);
		id29.setChecked(isCheck);
		if (isCheck){
			mapFunctions.put(26, 26);
			mapFunctions.put(27, 27);
			mapFunctions.put(28, 28);
			mapFunctions.put(29, 29);
		}else {
			mapFunctions.remove(26);
			mapFunctions.remove(27);
			mapFunctions.remove(28);
			mapFunctions.remove(29);
		}
	}

	private void chechGroupShipment (boolean isCheck){
		id22.setChecked(isCheck);
		id23.setChecked(isCheck);
		id24.setChecked(isCheck);
		id25.setChecked(isCheck);
		if (isCheck){
			mapFunctions.put(22, 22);
			mapFunctions.put(23, 23);
			mapFunctions.put(24, 24);
			mapFunctions.put(25, 25);
		}else {
			mapFunctions.remove(22);
			mapFunctions.remove(23);
			mapFunctions.remove(24);
			mapFunctions.remove(25);
		}
	}
	private void chechGroupCompa (boolean isCheck){
		id18.setChecked(isCheck);
		id19.setChecked(isCheck);
		id20.setChecked(isCheck);
		id21.setChecked(isCheck);
		if (isCheck){
			mapFunctions.put(18, 18);
			mapFunctions.put(19, 19);
			mapFunctions.put(20, 20);
			mapFunctions.put(21, 21);
		}else {
			mapFunctions.remove(18);
			mapFunctions.remove(19);
			mapFunctions.remove(20);
			mapFunctions.remove(21);
		}
	}
	private void chechGroupStudies (boolean isCheck){
		id11.setChecked(isCheck);
		id12.setChecked(isCheck);
		id13.setChecked(isCheck);
		id14.setChecked(isCheck);
		id15.setChecked(isCheck);
		id16.setChecked(isCheck);
		id17.setChecked(isCheck);
		
		if (isCheck){
			mapFunctions.put(11, 11);
			mapFunctions.put(12, 12);
			mapFunctions.put(13, 13);
			mapFunctions.put(14, 14);
			mapFunctions.put(15, 15);
			mapFunctions.put(16, 16);
			mapFunctions.put(17, 17);
		}else {
			mapFunctions.remove(11);
			mapFunctions.remove(12);
			mapFunctions.remove(13);
			mapFunctions.remove(14);
			mapFunctions.remove(15);
			mapFunctions.remove(16);
			mapFunctions.remove(17);
			
		}
	}
	private void chechGroupStoreLoc (boolean isCheck){
		id5.setChecked(isCheck);
		id6.setChecked(isCheck);
		id7.setChecked(isCheck);
		id8.setChecked(isCheck);
		id9.setChecked(isCheck);
		id10.setChecked(isCheck);
		
		if (isCheck){
			mapFunctions.put(5, 5);
			mapFunctions.put(6, 6);
			mapFunctions.put(7, 7);
			mapFunctions.put(8, 8);
			mapFunctions.put(9, 9);
			mapFunctions.put(10, 10);
		}else {
			mapFunctions.remove(5);
			mapFunctions.remove(6);
			mapFunctions.remove(7);
			mapFunctions.remove(8);
			mapFunctions.remove(9);
			mapFunctions.remove(10);
			
		}
	}
	private void checkGroupStudyTem(boolean isCheck){
		id1.setChecked(isCheck);
		id2.setChecked(isCheck);
		id3.setChecked(isCheck);
		id4.setChecked(isCheck);
		if (isCheck){
			mapFunctions.put(1, 1);
			mapFunctions.put(2, 2);
			mapFunctions.put(3, 4);
			mapFunctions.put(4, 4);
		}else {
			mapFunctions.remove(1);
			mapFunctions.remove(2);
			mapFunctions.remove(3);
			mapFunctions.remove(4);
			
		}
	}

	private int getValueCheckbox (Checkbox item){
		return Integer.parseInt(item.getValue().toString());
	}
	private void checkCheckBYRole(int idRole){
		switch (idRole){
		case ConstantsDNA.ROLE_RESEARCHER_ASSISTENT:
				id30.setChecked(false);
				mapFunctions.remove(getValueCheckbox(id30));
				id31.setChecked(false);
				mapFunctions.remove(getValueCheckbox(id31));
				id32.setChecked(false);
				mapFunctions.remove(getValueCheckbox(id32));
				id33.setChecked(false);
				mapFunctions.remove(getValueCheckbox(id33));
			break;
		case ConstantsDNA.ROLE_ASSISTENT:
			id1.setChecked(false);
			//mapFunctions.remove(getValueCheckbox(id1));
			id4.setChecked(false);
			mapFunctions.remove(getValueCheckbox(id4));
			id5.setChecked(false);
			//mapFunctions.remove(getValueCheckbox(id5));
			id8.setChecked(false);
			mapFunctions.remove(getValueCheckbox(id8));
			id11.setChecked(false);
			//mapFunctions.remove(getValueCheckbox(id11));
			id14.setChecked(false);
			mapFunctions.remove(getValueCheckbox(id14));
			id18.setChecked(false);
			//mapFunctions.remove(getValueCheckbox(id18));
			id21.setChecked(false);
			mapFunctions.remove(getValueCheckbox(id21));
			id22.setChecked(false);
			//mapFunctions.remove(getValueCheckbox(id22));
			id25.setChecked(false);
			mapFunctions.remove(getValueCheckbox(id25));
			id26.setChecked(false);
			//mapFunctions.remove(getValueCheckbox(id26));
			id29.setChecked(false);
			mapFunctions.remove(getValueCheckbox(id29));
			id30.setChecked(false);
			//mapFunctions.remove(getValueCheckbox(id30));
			id31.setChecked(false);
			mapFunctions.remove(getValueCheckbox(id31));
			id32.setChecked(false);
			mapFunctions.remove(getValueCheckbox(id32));
			id33.setChecked(false);
			mapFunctions.remove(getValueCheckbox(id33));
			id34.setChecked(false);
			//mapFunctions.remove(getValueCheckbox(id34));
			id37.setChecked(false);
			mapFunctions.remove(getValueCheckbox(id37));
			id38.setChecked(false);
			//mapFunctions.remove(getValueCheckbox(id38));
			id41.setChecked(false);
			mapFunctions.remove(getValueCheckbox(id41));
			id42.setChecked(false);
			//mapFunctions.remove(getValueCheckbox(id42));
			id45.setChecked(false);
			mapFunctions.remove(getValueCheckbox(id45));
			break;
		}
	}

	private void loadMap (){
		for (int i = 1 ; i <= 45; i++ ){
			Integer integer = new Integer(i);
			mapFunctions.put(integer, integer);
		}
	}
	private void initChecks(){
		id1 = (Checkbox)getFellow("id1");
		id2 = (Checkbox)getFellow("id2");
		id3 = (Checkbox)getFellow("id3");
		id4 = (Checkbox)getFellow("id4");
		id5 = (Checkbox)getFellow("id5");
		id6 = (Checkbox)getFellow("id6");
		id7 = (Checkbox)getFellow("id7");
		id8 = (Checkbox)getFellow("id8");
		id9 = (Checkbox)getFellow("id9");
		id10 = (Checkbox)getFellow("id10");
		id11 = (Checkbox)getFellow("id11");
		id12 = (Checkbox)getFellow("id12");
		id13 = (Checkbox)getFellow("id13");
		id14 = (Checkbox)getFellow("id14");
		id15 = (Checkbox)getFellow("id15");
		id16 = (Checkbox)getFellow("id16");
		id17 = (Checkbox)getFellow("id17");
		id18 = (Checkbox)getFellow("id18");
		id19 = (Checkbox)getFellow("id19");
		id20 = (Checkbox)getFellow("id20");
		id21 = (Checkbox)getFellow("id21");
		id22 = (Checkbox)getFellow("id22");
		id23 = (Checkbox)getFellow("id23");
		id24 = (Checkbox)getFellow("id24");
		id25 = (Checkbox)getFellow("id25");
		id26 = (Checkbox)getFellow("id26");
		id27 = (Checkbox)getFellow("id27");
		id28 = (Checkbox)getFellow("id28");
		id29 = (Checkbox)getFellow("id29");
		id30 = (Checkbox)getFellow("id30");
		id31 = (Checkbox)getFellow("id31");
		id32 = (Checkbox)getFellow("id32");
		id33 = (Checkbox)getFellow("id33");
		id34 = (Checkbox)getFellow("id34");
		id35 = (Checkbox)getFellow("id35");
		id36 = (Checkbox)getFellow("id36");
		id37 = (Checkbox)getFellow("id37");
		id38 = (Checkbox)getFellow("id38");
		id39 = (Checkbox)getFellow("id39");
		id40 = (Checkbox)getFellow("id40");
		id41 = (Checkbox)getFellow("id41");
		id42 = (Checkbox)getFellow("id42");
		id43 = (Checkbox)getFellow("id43");
		id44 = (Checkbox)getFellow("id44");
		id45 = (Checkbox)getFellow("id45");
		idWindowFuntions = (Window)getFellow("idWindowFuntions");
	}

}
