package jp.co.strrugleforlife.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import jp.co.strrugleforlife.domain.Item;


@Mapper
public interface ItemMapper {
  List<Item> findAll();

  Item findOne(Integer id);

  void save(Item item);

  void update(Item item);

  void delete(Integer id);
}
