package com.ruobilin.mall.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruobilin.mall.entity.Category;
import com.ruobilin.mall.entity.Recommended;
import com.ruobilin.mall.entity.RecommendedGoods;
import com.ruobilin.mall.mapper.FrontRecommendedMapper;
@Service
@Transactional
public class FrontRecommendedService {
	@Autowired
	private FrontRecommendedMapper recommendedMapper;
	
	/**
	 * 查询所有
	 * @author weizhaohui
	 *
	 * 2015年8月20日上午11:38:41
	 *
	 */
	public List<Recommended> findAllLevel(){
		return recommendedMapper.findAllLevel();
	}
	
	/**
	 * 只显示二级节点
	 * @author weizhaohui
	 *
	 * 2015年8月20日下午2:04:55
	 *
	 */
	public List<Recommended> findAllForTree(){
		List<Recommended> recViewList = new ArrayList<Recommended>();
		List<Recommended> recList = recommendedMapper.findAllLevel();
		if(recList == null || recList.size()<1){
			return null;
		}
		
		//遍历所有节点
		for(Recommended recommended : recList){
			
			//二级节点为父节点为0
			if(recommended.getParentId().equals("0")){
				
			//获取当前二级节点下的所有三级节点
				
				  //获取当前二级节点的ID
				  Integer recommendedId=recommended.getId();
				  
				  //根据当前二级节点的ID获取其所属的所有三级节点(二级节点的id相当于三级节点的parentId)
				  List<Recommended> thirdRecommendeds=findThirdLevel(recommendedId);
				  
				  //遍历三级节点
				  for(Recommended thirdRecommended : thirdRecommendeds){
					  
					  //获取当前节点id
					  Integer thirdRecommendedId=thirdRecommended.getId();
					  
					  //获取当前节点下所有商品
					  List<Map<String,String>> homeProducts=getHomeProducts(thirdRecommendedId);
//					  List<RecommendedGoods>  thirdRecommendedGoods=getThirdRecommendedGoods(thirdRecommendedId);
					  
					  //装入商品中
					  thirdRecommended.setHomeProducts(homeProducts);
				  }

				  //装入children中
				  recommended.setChildren(thirdRecommendeds);
				  
			//装入集合中,返回
			recViewList.add(recommended);
			}
		}
		return recViewList;
	}



	/**
	 * 根据二级id获取三级信息
	 * @param 
	 * @author weizhaohui
	 *
	 * 2015年8月20日下午3:58:56
	 *
	 */
	private List<Recommended> findThirdLevel(Integer parentId) {
		//根据传入进来的二级id查询其下属所有三级节点(传入父ID)
		List<Recommended> thirdRecommendeds=recommendedMapper.findRecommendedByParentId(parentId.toString());
		return thirdRecommendeds;
	}
	
	/**
	 * 根据当前id获取其所属的所有商品
	 * @author weizhaohui
	 *
	 * 2015年8月20日下午4:24:48
	 *
	 */
	private List<RecommendedGoods> getThirdRecommendedGoods(Integer thirdRecommendedId) {
		List<RecommendedGoods> thirdRecommendedGoods= recommendedMapper.findRecommendedGoodsById(thirdRecommendedId);
		return thirdRecommendedGoods;
		
	}
	
	/**
	 * 根据推荐表id获取其所属的商品信息,用于首页展示
	 * @param thirdRecommendedId
	 * @return
	 */
	private List<Map<String,String>> getHomeProducts(Integer thirdRecommendedId){
		return recommendedMapper.getHomeProducts(thirdRecommendedId);
	}
}
