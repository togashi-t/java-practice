package app;

import java.util.Random;
import java.util.stream.IntStream;

public class FamilySimulator {

    // 黙示録:現在の文明が滅んだあとの新しい世界で、女王は出生率について心の底から心配していました。
    // そのため女王は、すべての家族は1人女の子を持つか、そうでなければ多額の罰金を支払わねばならないという法令を作りました。
    // すべての家族がこの規則を守るとすると、つまり女の子が生まれるまで子を持ち続け、生まれた時点で子供を増やすのをやめるとー新しい世代の男女比はどのようになるでしょうか?
    // (生まれる子どもが男である確率も女である確率も同じであると仮定してください。)


    // タプルを定義
    private record Pair<A, B>(A _1, B _2) {}

    // runOneFamilyの主な処理を担うメソッド
    // Javaではメソッド内に他のメソッドを定義することはできないためここに定義している
    private static Pair<Integer, Integer> simulate(int boys, Random random) {
        if (random.nextBoolean()) {
            return new Pair<>(boys, 1);
        } else {
            return simulate(boys + 1, random);
        }
    }

    // 1つの家族における子供の男女数をシミレーションするメソッド
    private static Pair<Integer, Integer> runOneFamily(Random random) {
        return simulate(0, random);
    }



    // N件の家族における子供の男女数合計をシミュレーションするメソッド
    public static Pair<Integer, Integer> runNFamily(int n) {
        Random random = new Random();

        // reduceの初期値の型はコレクションの要素の型と一致させる必要がある
        return IntStream.range(0, n)
                .mapToObj(i -> runOneFamily(random))
                .reduce(new Pair<>(0, 0), (acc, result) -> new Pair<>(acc._1 + result._1, acc._2 + result._2));
    }



    public static void main(String... args) {
        Pair<Integer, Integer> result = runNFamily(10000000);
        System.out.println((double) result._2 / (result._1 + result._2));
    }




}
