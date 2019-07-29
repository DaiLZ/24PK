package test.com.a24;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.support.v4.app.Fragment;

import test.com.a24.bean.QuestionBean;

//abstract 抽象类 将不能生成对象实例 但可做为对象变量声明的类型
// 也就是编译时类型，抽象类就像当于一类的半成品，
// 需要子类继承并覆盖其中的抽象方法。
public class AnswerFragment extends Fragment{

    QuestionBean qBean = null;
    public AnswerFragment(){}
    public void AnswerFragment(QuestionBean qBean) {
        this.qBean = qBean;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        ((TextView) getActivity().findViewById(R.id.num1)).setText(qBean.getNum1()+"");
        ((TextView) getActivity().findViewById(R.id.num2)).setText(qBean.getNum2()+"");
        ((TextView) getActivity().findViewById(R.id.num3)).setText(qBean.getNum3()+"");
        ((TextView) getActivity().findViewById(R.id.num4)).setText(qBean.getNum4()+"");
        ((TextView) getActivity().findViewById(R.id.Qn)).setText("第"+qBean.getId()+"题");
        ((TextView) getActivity().findViewById(R.id.Answer)).setText(qBean.getAnswer());
        ((Button) getActivity().findViewById(R.id.button_num1)).setText(qBean.getNum1()+"");
        ((Button) getActivity().findViewById(R.id.button_num2)).setText(qBean.getNum2()+"");
        ((Button) getActivity().findViewById(R.id.button_num3)).setText(qBean.getNum3()+"");
        ((Button) getActivity().findViewById(R.id.button_num4)).setText(qBean.getNum4()+"");
        super.onAttach(context);
    }
}